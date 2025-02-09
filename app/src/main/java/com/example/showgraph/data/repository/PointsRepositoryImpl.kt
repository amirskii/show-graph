package com.example.showgraph.data.repository

import com.example.showgraph.data.remote.Point
import com.example.showgraph.data.remote.PointsApi
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.repository.PointsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PointsRepositoryImpl(
    private val api: PointsApi,
    private val defaultDispatcher: CoroutineDispatcher
) : PointsRepository {
    override fun getPoints(count: Int): Flow<Resource<List<Point>>> =
        flow {
            emit(Resource.Loading())
            val data = api.getPoints(count).points
            emit(Resource.Success(data))
        }.catch {
            emit(Resource.Error(it.message ?: "unknown error"))
        }.flowOn(defaultDispatcher)
}