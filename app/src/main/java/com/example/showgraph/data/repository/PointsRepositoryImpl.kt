package com.example.showgraph.data.repository

import com.example.showgraph.data.remote.PointsApi
import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val api: PointsApi
) : PointsRepository {
    override suspend fun getPoints(count: Int): List<Point> {
        // map to domain to models
        return api.getPoints(count)
            .points.map { Point(it.x, it.y) }
    }

}