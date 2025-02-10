package com.example.showgraph.presentation.chart

import com.example.showgraph.presentation.model.ChartDataPm
import kotlinx.coroutines.flow.Flow

interface ChartViewModel {
    val uiState: Flow<ChartUiState>
}

data class ChartUiState(
    val points: ChartDataPm? = null,
    val loading: Boolean = false,
    val error: String? = null
)
