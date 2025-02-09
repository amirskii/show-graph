package com.example.showgraph.domain.repository

import com.example.showgraph.domain.model.Point


interface PointsRepository {
    suspend fun getPoints(count: Int): List<Point>
}