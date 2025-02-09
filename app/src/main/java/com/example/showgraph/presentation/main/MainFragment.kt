package com.example.showgraph.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.showgraph.databinding.FragmentMainBinding
import com.example.showgraph.presentation.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
) {
    private val viewModel by viewModel<MainViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observe()
    }

    private fun setupUi() {
        with(binding) {
            button.setOnClickListener {
                val count = editText.text.toString().toIntOrNull() ?: 0
                if (count > 0) {
                    viewModel.getPoints(count)
                } else {
                    showError("Введите корректное число точек")
                }
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progress.isVisible = state.loading

                    if (!state.error.isNullOrBlank()) {
                        binding.errorTextView.text = state.error
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect {
                    if (it is MainEvents.navigateToChartEvent) {
                        findNavController().navigate(MainFragmentDirections.toChartFragment(it.count))
                    }
                }
            }
        }
    }
}