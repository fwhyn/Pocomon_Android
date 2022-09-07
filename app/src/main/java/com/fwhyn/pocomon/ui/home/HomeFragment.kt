package com.fwhyn.pocomon.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.data.room.RoomPokemonDatabase
import com.fwhyn.pocomon.data.utils.Constants
import com.fwhyn.pocomon.databinding.FragmentHomeBinding
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.ui.common.PokeRecyclerViewAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

var pokemonsDisplayed: Int = 0
var loading: Boolean = false
// TODO (add maximum failure and timeout)
var failureFlag = false
var allTypesList: MutableList<Pokemon> = mutableListOf()
var toLoadList: MutableList<Pokemon> = mutableListOf()
var pagingFlag: Boolean = true

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonsDisplayed = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViews()
        setupObservers()

//        val db = context?.let { RoomPokemonDatabase.getDatabase(it) }
//
//        val dbDao = db?.roomPokemonDao()
//        GlobalScope.launch {
//            val test = dbDao?.isPokemonSaved(1)
//            Log.d("fwhyn_test", "onCreateView: $test")
//        }

        return viewBinding.root
    }

    private fun onLoadingSuccess(pokemonList: MutableList<Pokemon>, adapter: PokeRecyclerViewAdapter) {
        with(viewBinding) {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            noInternetLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        adapter.submitList(pokemonList.toMutableList())
        pokemonsDisplayed = pokemonList.size
        loading = false
    }

    private fun onLoadingFailure() {
        with(viewBinding) {
            if (pokemonsDisplayed == 0) noInternetLayout.visibility = View.VISIBLE
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

    private fun setupObservers() {
        viewModel.myPokemon.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewModel.Result.Failure -> onLoadingFailure()
                is HomeViewModel.Result.Loading -> loading = true
                is HomeViewModel.Result.Success -> onLoadingSuccess(it.value, adapter)
            }
        }

        viewModel.myTypePokemon.observe(viewLifecycleOwner) { result ->
            if (result is HomeViewModel.Result.Success && allTypesList.isEmpty()) {
                toLoadList.clear()

                result.value.results.forEach {
                    val trimmedUrl = it.pokemon.url?.dropLast(1)
                    it.pokemon.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                    if (it.pokemon.id <= Constants.TOTAL_POKEMONS) allTypesList.add(it.pokemon)
                }

                setupAdapter(allTypesList.size)

                for (i in 0 until Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(result.value.results[i].pokemon)
                }

                viewModel.getPokemon(toLoadList)
            }
        }

        viewModel.myPokemonNamesList.observe(viewLifecycleOwner) { result ->
            if (result is HomeViewModel.Result.Success && allTypesList.isEmpty()) {
                toLoadList.clear()

                result.value.results.forEach {
                    val trimmedUrl = it.url?.dropLast(1)
                    it.id = trimmedUrl!!.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                    if (it.id <= Constants.TOTAL_POKEMONS) allTypesList.add(it)
                }

                setupAdapter(allTypesList.size)

                for (i in 0 until Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(result.value.results[i])
                }
                viewModel.getPokemon(toLoadList)
            }
        }
    }

    private fun setupViews() {
        setupAdapter(allTypesList.size)
        showLoadingAnimation()
    }

    private fun setupAdapter(lastPosition: Int) {
        adapter = PokeRecyclerViewAdapter(clickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(it)
            findNavController(requireView()).navigate(action)
        }, favoriteButtonClickListener = { pokemon: Pokemon, isSelected: Boolean ->
            if (isSelected) {
                viewModel.deleteFavoritePokemon(pokemon)
            } else {
                viewModel.addFavoritePokemon(pokemon)
            }
        }, true, isPokemonFavorite = {
            return@PokeRecyclerViewAdapter viewModel.isPokemonFavorite(it)
        }, lastPosition)
        setupRecycler(adapter)
    }

    private fun setupRecycler(adapter: PokeRecyclerViewAdapter) {
        with(viewBinding) {
            recyclerView.adapter = adapter
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
                    if (pagingFlag) callGetPokemon()
                }
            })
        }
    }

    private fun showLoadingAnimation() {
        if (pokemonsDisplayed == 0) {
            viewBinding.shimmerLayout.startShimmer()
            viewBinding.shimmerLayout.visibility = View.VISIBLE
        }
    }

    private fun callGetPokemon() {
        if (!viewBinding.recyclerView.canScrollVertically(1) && !loading && pokemonsDisplayed >= Constants.POKEMONS_LOAD_LIMIT) {
            toLoadList.clear()
            if (allTypesList.size - pokemonsDisplayed >= 20) {
                for (i in pokemonsDisplayed until pokemonsDisplayed + Constants.POKEMONS_LOAD_LIMIT) {
                    toLoadList.add(allTypesList[i])
                }
                pokemonsDisplayed += Constants.POKEMONS_LOAD_LIMIT
            } else if (pokemonsDisplayed < allTypesList.size) {
                for (i in pokemonsDisplayed until allTypesList.size) {
                    toLoadList.add(allTypesList[i])
                }
                pokemonsDisplayed = allTypesList.size
            }
            viewModel.getPokemon(toLoadList)
        }
    }

    private fun onConnectionRestored() {
        if (failureFlag) {
            activity?.runOnUiThread {
                viewBinding.noInternetLayout.visibility = View.GONE
            }

            if (toLoadList.isNotEmpty()) {
                viewModel.getPokemon(toLoadList)
                activity?.runOnUiThread { showLoadingAnimation() }
                failureFlag = false
            } else {
                TODO("if pokemon list empty")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        allTypesList.clear()
    }
}