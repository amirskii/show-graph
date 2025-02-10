package com.example.showgraph.presentation.chart

import com.example.showgraph.base.BaseViewModelTest
import com.example.showgraph.domain.model.Point
import com.example.showgraph.domain.model.Resource
import com.example.showgraph.domain.usecase.FetchPointsUseCase
import com.example.showgraph.presentation.mapper.ChartDataPmMapper
import com.example.showgraph.presentation.model.ChartDataPm
import com.example.showgraph.presentation.model.PointPm
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ChartViewModelTest: BaseViewModelTest() {
    @MockK
    lateinit var fetchPointsUseCase: FetchPointsUseCase

    @MockK
    lateinit var chartDataPmMapper: ChartDataPmMapper

    private lateinit var viewModel: ChartViewModel

    private fun buildViewModel(useCase: FetchPointsUseCase = fetchPointsUseCase): ChartViewModelImpl {
        return ChartViewModelImpl(
            count = 2,
            useCase,
            chartDataPmMapper
        )
    }

    @Before
    fun setUp() {
        coEvery { fetchPointsUseCase(any(), any()) }.coAnswers {
            flow {
                emit(Resource.Loading())
                emit(Resource.Success(listOf(mockk<Point>(), mockk<Point>())))
            }
        }

        every {
            chartDataPmMapper.map(any())
        } returns ChartDataPm(points = listOf(PointPm("x", "y"), PointPm("x2", "y2")), listOf())

        viewModel = buildViewModel()
    }


    @Test
    fun `on init should call use case with using cache`() =
        runTest {
            coVerify(exactly = 1) {
                fetchPointsUseCase(2, true)
            }
        }

    @Test
    fun `on init should call placesPmMapper`() =
        runTest {
            verify(exactly = 1) {
                chartDataPmMapper.map(any())
            }
        }


    @Test
    fun `on init ui state should change to list of 2 points`() =
        runTest {
            // expect success
            assertEquals(2, viewModel.uiState.value.chartData?.points?.size)
        }

    @Test
    fun `on init ui state should change to loading`() =
        runTest {
            val fetchPointsUseCaseMock = mockk<FetchPointsUseCase>()
            coEvery { fetchPointsUseCaseMock(any(), any()) }.coAnswers {
                flow {
                    emit(Resource.Loading())
                }
            }

            viewModel = buildViewModel(useCase = fetchPointsUseCaseMock)

            // expect error
            assertEquals(true, viewModel.uiState.value.loading)
        }


    @Test
    fun `on init ui state should change to error`() =
        runTest {
            val fetchPointsUseCaseMock = mockk<FetchPointsUseCase>()
            coEvery { fetchPointsUseCaseMock(any(), any()) }.coAnswers {
                flow {
                    emit(Resource.Error("some error"))
                }
            }

            viewModel = buildViewModel(useCase = fetchPointsUseCaseMock)

            // expect error
            assertEquals("some error", viewModel.uiState.value.error)
        }
}