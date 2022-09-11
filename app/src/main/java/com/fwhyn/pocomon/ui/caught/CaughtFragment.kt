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
import com.fwhyn.pocomon.ui.utils.UiConstant.Companion.CAUGHT_ACTIVITY_CODE
import com.fwhyn.pocomon.ui.utils.UiUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO(add multiple removes)
class CaughtFragment : Fragment() {
    private val viewModel by viewModel<CaughtViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewBinding = FragmentCaughtBinding.inflate(inflater, container, false)

        setupAdapter().also {
            setupRecyclerView(it, viewBinding)
            setupObservers(it, viewBinding)
        }

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getCaughtPokemonList()
    }

    // functions
    private fun setupAdapter(): PokeRecyclerViewAdapter {
        return PokeRecyclerViewAdapter(
            clickListener = { UiUtil.startInfoActivity(requireActivity(), launcher, it, CAUGHT_ACTIVITY_CODE) },
            false,
            isPokemonCaught = { return@PokeRecyclerViewAdapter viewModel.isPokemonCaught(it) },
            null,
            true)
    }

    private fun setupRecyclerView(adapter: PokeRecyclerViewAdapter, viewBinding: FragmentCaughtBinding) {
        with(viewBinding.recyclerView) {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.grid_column_count))
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_color))
            hasFixedSize()
        }
    }

    private fun setupObservers(adapter: PokeRecyclerViewAdapter, viewBinding: FragmentCaughtBinding) {
        viewModel.myCaughtPokemons.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewBinding.emptyCaughtLayout.visibility = View.VISIBLE
            } else {
                viewBinding.emptyCaughtLayout.visibility = View.GONE
            }
            adapter.submitList(it)
        }
    }
}