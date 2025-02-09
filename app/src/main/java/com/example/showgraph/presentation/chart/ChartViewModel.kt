package com.example.showgraph.presentation.chart

import com.example.showgraph.domain.model.Point
import kotlinx.coroutines.flow.Flow

interface ChartViewModel {
    val uiState: Flow<ChartUiState>
}

data class ChartUiState(
    val points: List<Point>? = null,
    val loading: Boolean = false,
    val error: String? = null
)
