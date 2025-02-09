package com.example.showgraph.presentation.di

import com.example.showgraph.presentation.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainInjectionModule {

    val module = module {
        viewModel {
            MainViewModelImpl(
                pointsRepository = get()
            )
        }
    }
}