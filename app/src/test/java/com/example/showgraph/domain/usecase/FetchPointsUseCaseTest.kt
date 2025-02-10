package com.example.showgraph.domain.usecase

import com.example.showgraph.base.BaseViewModelTest
import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.repository.PointsRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FetchPointsUseCaseTest : BaseViewModelTest() {
    @MockK
    private lateinit var pointsRepository: PointsRepository

    private val points = listOf(Point(1f, 1f), Point(0f, 0f))

    override fun setup() {
        super.setup()

        coEvery {
            pointsRepository.fetchPoints(any(), any())
        } returns points
    }

    @Test
    fun `use case return sorted list of points`() =
        runTest {
            // given
            val useCase = initWithMocks {}

            // when
            val result = useCase(2, false)
                .toList()
                .last()

            // expect
            val sortedList = listOf(Point(0f, 0f), Point(1f, 1f))
            assertEquals(sortedList, (result as Resource.Success).data)
        }

    @Test
    fun `use case return error message if there is an error`() = runTest {
        // given
        val useCase = initWithMocks {
            coEvery {
                pointsRepository.fetchPoints(any(), any())
            } throws IOException("no internet error")
        }

        // when
        val result = useCase(2, false)
            .toList()
            .last()

        // expect
        assertEquals("no internet error", (result as Resource.Error).message)
    }

    private fun initWithMocks(mockBlock: () -> Unit): FetchPointsUseCaseImpl {
        mockBlock()
        return FetchPointsUseCaseImpl(
            pointsRepository,
            UnconfinedTestDispatcher()
        )
    }

}