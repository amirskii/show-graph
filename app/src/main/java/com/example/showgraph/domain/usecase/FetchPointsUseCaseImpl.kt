package com.example.showgraph.domain.usecase

import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.repository.PointsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchPointsUseCaseImpl(
    private val pointsRepository: PointsRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : FetchPointsUseCase {

    override fun invoke(count: Int): Flow<Resource<List<Point>>> = flow {
        emit(Resource.Loading())
        val data = pointsRepository.getPoints(count).sortedBy { it.x }
        emit(Resource.Success(data))
    }.catch {
        emit(Resource.Error(it.message ?: "unknown error"))
    }.flowOn(defaultDispatcher)
}