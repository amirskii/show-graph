package com.example.showgraph.presentation.mapper

import com.example.showgraph.domain.model.Point
import com.example.showgraph.presentation.model.ChartDataPm
import com.example.showgraph.presentation.model.PointPm
import com.github.mikephil.charting.data.Entry

class ChartDataPmMapper {
    fun map(points: List<Point>): ChartDataPm {
        val header = PointPm("x", "y")
        val chartEntries = points.map {
            Entry(it.x, it.y)
        }

        return ChartDataPm(
            points = listOf(header) + points.map { PointPm("${it.x}", "${it.y}")},
            chartEntries
        )
    }
}