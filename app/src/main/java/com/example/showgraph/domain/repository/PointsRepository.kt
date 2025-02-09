package com.example.showgraph.domain.repository

import com.example.showgraph.domain.model.Point


interface PointsRepository {
    suspend fun fetchPoints(count: Int, useCache: Boolean): List<Point>
}