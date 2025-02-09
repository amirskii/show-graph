package com.example.showgraph.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showgraph.domain.repository.PointsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModelImpl(
    private val pointsRepository: PointsRepository
) : MainViewModel, ViewModel() {

    override val uiState = MutableStateFlow(MainUiState())

    private val eventsChannel = Channel<MainEvents>()
    override val events: Flow<MainEvents> = eventsChannel.receiveAsFlow()

    override fun getPoints(count: Int) {
        uiState.update { state ->
            state.copy(
                loading = true,
                points = null
            )
        }
        viewModelScope.launch {
            pointsRepository.getPoints(count)
                .catch {
                    uiState.update { state -> state.copy(
                        error = it.message ?: "Unknown error",
                        loading = false
                    ) }
                }
                .onEach { payload ->
                    uiState.update { state ->
                        state.copy(
                            points = payload.data,
                            loading = false,
                            error = null
                        )
                    }
                }
                .collect()
        }
    }

}