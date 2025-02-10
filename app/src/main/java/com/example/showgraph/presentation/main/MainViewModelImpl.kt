package com.example.showgraph.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.usecase.FetchPointsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val fetchPointsUseCase: FetchPointsUseCase
) : MainViewModel, ViewModel() {

    override val uiState = MutableStateFlow(MainUiState())
    private val eventsChannel = Channel<MainEvents>()
    override val events: Flow<MainEvents> = eventsChannel.receiveAsFlow()

    override fun getPoints(count: Int) {
        viewModelScope.launch {
            fetchPointsUseCase(count).collect {
                uiState.update { state ->
                    when (it) {
                        is Resource.Success -> {
                            eventsChannel.send(MainEvents.navigateToChartEvent(count))
                            state.copy(
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