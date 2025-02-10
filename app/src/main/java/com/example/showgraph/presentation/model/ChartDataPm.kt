package com.example.showgraph.presentation.model

import com.github.mikephil.charting.data.Entry

data class ChartDataPm(
    val points: List<PointPm>,
    val chartEntries: List<Entry>
)

data class PointPm(val x: String, val y: String)