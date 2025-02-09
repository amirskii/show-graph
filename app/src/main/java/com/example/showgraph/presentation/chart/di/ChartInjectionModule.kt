package com.example.showgraph.presentation.chart.di

import com.example.showgraph.presentation.chart.ChartViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChartInjectionModule {

    val module = module {
        viewModel { params ->
            ChartViewModelImpl(
                count = params.get(),
                fetchPointsUseCase = get()
            )
        }
    }
}