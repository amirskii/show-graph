package com.example.showgraph.presentation.chart

import com.example.showgraph.presentation.model.ChartDataPm
import kotlinx.coroutines.flow.StateFlow

interface ChartViewModel {
    val uiState: StateFlow<ChartUiState>
}

data class ChartUiState(
    val chartData: ChartDataPm? = null,
    val loading: Boolean = false,
    val error: String? = null
)
