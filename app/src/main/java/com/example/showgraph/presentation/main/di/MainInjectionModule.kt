package com.example.showgraph.presentation.main.di

import com.example.showgraph.domain.usecase.FetchPointsUseCase
import com.example.showgraph.domain.usecase.FetchPointsUseCaseImpl
import com.example.showgraph.presentation.main.MainViewModelImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object MainInjectionModule {

    val module = module {
        factory<FetchPointsUseCase> {
            FetchPointsUseCaseImpl(
                pointsRepository = get(),
                defaultDispatcher = Dispatchers.IO
            )
        }

        viewModel {
            MainViewModelImpl(
                fetchPointsUseCase = get()
            )
        }
    }
}