package com.example.showgraph.presentation.main

import com.example.showgraph.data.remote.Point
import kotlinx.coroutines.flow.Flow

interface MainViewModel {
    val uiState: Flow<MainUiState>
    val events: Flow<MainEvents>
    fun getPoints(count: Int)
}

data class MainUiState(
    val points: List<Point>? = null,
    val loading: Boolean = false,
    val error: String? = null
)

sealed class MainEvents {
    data class ErrorEvent(val message: String) : MainEvents()
}

