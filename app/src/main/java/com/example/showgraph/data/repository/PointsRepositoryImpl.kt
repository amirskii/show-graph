package com.example.showgraph.data.repository

import com.example.showgraph.data.remote.PointsApi
import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val api: PointsApi
) : PointsRepository {
    // simple memory cache
    private val cache = mutableListOf<Point>()

    override suspend fun fetchPoints(count: Int, useCache: Boolean): List<Point> {
        return if (useCache && cache.isNotEmpty()) cache else api.getPoints(count)
            // map to domain to models
            .points.map { Point(it.x, it.y) }
            // cache
            .also {
                cache.clear()
                cache.addAll(it)
            }
    }
}