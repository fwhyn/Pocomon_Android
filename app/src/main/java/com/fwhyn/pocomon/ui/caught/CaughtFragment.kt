package com.fwhyn.pocomon.ui.caught

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.databinding.FragmentCaughtBinding
import com.fwhyn.pocomon.ui.common.recyclerview.PokeRecyclerViewAdapter
import com.fwhyn.pocomon.ui.launcher
import com.fwhyn.pocomon.ui.utils.UiConstant
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO(add multiple removes)
class CaughtFragment : Fragment() {
    private var binding: FragmentCaughtBinding? = null
    private lateinit var viewBinding: FragmentCaughtBinding

    private val viewModel by viewModel<CaughtViewModel>()
    private lateinit var adapter: PokeRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getCaughtPokemonList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = FragmentCaughtBinding.inflate(inflater, container, false)
        }
        viewBinding = binding!!
        setupObservers()
        setupAdapter()
        setupRecyclerView(adapter)
        return binding?.root
    }

    private fun setupObservers() {
        viewModel.myCaughtPokemons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isEmpty()) viewBinding.emptyCaughtLayout.visibility = View.VISIBLE
            else viewBinding.emptyCaughtLayout.visibility = View.GONE
        }
    }

    private fun setupAdapter() {
        adapter = PokeRecyclerViewAdapter(
            clickListener = { UiConstant.startInfoActivity(requireActivity(), launcher, it) },
            false,
            isPokemonCaught = { return@PokeRecyclerViewAdapter viewModel.isPokemonCaught(it) },
            null)
    }

    private fun setupRecyclerView(adapter: PokeRecyclerViewAdapter) {
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
        }
    }
}