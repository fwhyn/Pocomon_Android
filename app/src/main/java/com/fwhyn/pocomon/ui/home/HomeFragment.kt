package com.fwhyn.pocomon.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.data.utils.DataConstants
import com.fwhyn.pocomon.databinding.FragmentHomeBinding
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.ui.common.recyclerview.PokeRecyclerViewAdapter
import com.fwhyn.pocomon.ui.launcher
import com.fwhyn.pocomon.ui.utils.UiUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var loading: Boolean = true
    private var failureFlag = false // TODO(add maximum failure and timeout)

    private var shownPokemon: Int = 0

    private var toLoadList: MutableList<Pokemon> = mutableListOf()

    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var adapter: PokeRecyclerViewAdapter

    private val viewModel by viewModel<HomeViewModel>()

    // lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shownPokemon = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViews()
        setupObservers()

        return viewBinding.root
    }

    // function
    private fun setupViews() {
        showLoadingAnimation()
        setupAdapter()
    }

    private fun setupObservers() {
        viewModel.myPokemonNamesList.observe(viewLifecycleOwner) {
            // no implementation
        }

        viewModel.myPokemons.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewModel.Result.Failure -> onLoadingFailure()
                HomeViewModel.Result.Loading -> loading = true
                is HomeViewModel.Result.Success -> loading = !onLoadingSuccess(it.value)
            }
        }
    }

    private fun onLoadingSuccess(pokemonList: MutableList<Pokemon>): Boolean {
        with(viewBinding) {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            noInternetLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        // update last position
        adapter.lastPosition = viewModel.allPokemonsToLoad.size
        // add pokemon list to adapter
        adapter.submitList(pokemonList.toMutableList())
        shownPokemon = pokemonList.size

        return true
    }

    private fun onLoadingFailure() {
        with(viewBinding) {
            if (shownPokemon == 0) noInternetLayout.visibility = View.VISIBLE
            else Toast.makeText(context, resources.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT)
                .show()
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
        }
        failureFlag = true

        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        onConnectionRestored()
                    }
                })
            }
        }
    }

    private fun setupAdapter(lastPosition: Int = DataConstants.TOTAL_POKEMONS) {
        adapter = PokeRecyclerViewAdapter(
            clickListener = { UiUtil.startInfoActivity(requireActivity(), launcher, it) },
            true,
            isPokemonCaught = { return@PokeRecyclerViewAdapter viewModel.isPokemonCaught(it) },
            lastPosition)

        setupRecycler(adapter)
    }

    private fun setupRecycler(recyclerViewAdapter: PokeRecyclerViewAdapter) {
        with(viewBinding) {
            recyclerView.adapter = recyclerViewAdapter

            recyclerView.layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.grid_column_count))
            recyclerView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.bg_color
                )
            )
            recyclerView.hasFixedSize()
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                        callGetPokemon()
                }
            })
        }
    }

    private fun showLoadingAnimation() {
        if (shownPokemon == 0) {
            viewBinding.shimmerLayout.startShimmer()
            viewBinding.shimmerLayout.visibility = View.VISIBLE
        }
    }

    private fun callGetPokemon() {
        if (!viewBinding.recyclerView.canScrollVertically(1) && !loading && shownPokemon >= DataConstants.POKEMONS_LOAD_LIMIT) {
            toLoadList.clear()
            with (viewModel) {
                val limitedLoadList = getLimitedToLoad(shownPokemon)
                // if no more item to load
                if (limitedLoadList.size != 0) {
                    toLoadList.addAll(limitedLoadList)
                    shownPokemon += toLoadList.size
                    loadPokemon(toLoadList)
                }
            }
        }
    }

    private fun onConnectionRestored() {
        if (failureFlag) {
            activity?.runOnUiThread {
                viewBinding.noInternetLayout.visibility = View.GONE
            }

            if (toLoadList.isNotEmpty() && !loading) {
                viewModel.loadPokemon(toLoadList)
                activity?.runOnUiThread { showLoadingAnimation() }
                failureFlag = false
            } else {
                // TODO("if pokemon list empty")
//                viewModel.getAllPokemonNames()
            }
        }
    }
}