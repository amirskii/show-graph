package com.example.showgraph.presentation.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.usecase.FetchPointsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChartViewModelImpl(
    private val count: Int,
    private val fetchPointsUseCase: FetchPointsUseCase
) : ChartViewModel, ViewModel() {

    override val uiState = MutableStateFlow(ChartUiState())

    init {
        viewModelScope.launch {
            fetchPointsUseCase.invoke(count, useCache = true).collect {
                uiState.update { state ->
                    when (it) {
                        is Resource.Success -> {
                            state.copy(
                                points = it.data,
                                loading = false,
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            state.copy(
                                loading = false,
                                error = it.message
                            )
                        }

                        is Resource.Loading -> {
                            state.copy(
                                loading = true
                            )
                        }
                    }
                }
            }
        }
    }

}