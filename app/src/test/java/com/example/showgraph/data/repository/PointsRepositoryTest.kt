package com.example.showgraph.data.repository

import com.example.showgraph.base.BaseViewModelTest
import com.example.showgraph.data.remote.PointDto
import com.example.showgraph.data.remote.PointsApi
import com.example.showgraph.data.remote.PointsResponse
import com.example.showgraph.domain.model.Point
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PointsRepositoryTest: BaseViewModelTest() {

    @MockK
    private lateinit var api: PointsApi

    private val points = listOf(PointDto(1f, 1f), PointDto(0f, 0f))

    override fun setup() {
        super.setup()

        coEvery {
            api.getPoints(any())
        } returns PointsResponse(points)
    }

    @Test
    fun `fetchPoints return list of domain models`() =
        runTest {
            // given
            val pointsRepository = PointsRepositoryImpl(api)

            // when
            val result = pointsRepository.fetchPoints(2, false)

            // expect
            val domainModels = listOf(Point(1f, 1f), Point(0f, 0f))
            assertEquals(domainModels, result)
        }

    @Test
    fun `fetchPoints populates for first call and uses cache for every subsequent call`() =
        runTest {
            // given
            val pointsRepository = PointsRepositoryImpl(api)

            // when
            pointsRepository.fetchPoints(2, false)
            pointsRepository.fetchPoints(2, true)
            pointsRepository.fetchPoints(2, true)
            // expect
            coVerify(exactly = 1) { api.getPoints(any()) }
        }
}