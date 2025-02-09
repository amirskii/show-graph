package com.example.showgraph.domain.usecase

import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface FetchPointsUseCase {

    operator fun invoke(count: Int): Flow<Resource<List<Point>>>
}