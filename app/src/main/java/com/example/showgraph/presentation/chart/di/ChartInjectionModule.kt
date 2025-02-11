package com.example.showgraph.presentation.chart.di

import com.example.showgraph.presentation.chart.ChartViewModelImpl
import com.example.showgraph.presentation.mapper.ChartDataPmMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ChartInjectionModule {

    val module = module {
        viewModel { params ->
            ChartViewModelImpl(
                count = params.get(),
                fetchPointsUseCase = get(),
                chartDataPmMapper = ChartDataPmMapper()
            )
        }
    }
}