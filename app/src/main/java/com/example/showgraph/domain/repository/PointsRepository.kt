package com.example.showgraph.domain.repository

import com.example.showgraph.data.remote.Point
import com.example.showgraph.domain.model.Resource
import kotlinx.coroutines.flow.Flow


interface PointsRepository {
    fun getPoints(count: Int): Flow<Resource<List<Point>>>
}