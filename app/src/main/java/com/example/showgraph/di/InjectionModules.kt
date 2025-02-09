package com.example.showgraph.di

import com.example.showgraph.data.remote.di.NetworkInjectionModule
import com.example.showgraph.presentation.chart.di.ChartInjectionModule
import com.example.showgraph.presentation.main.di.MainInjectionModule


object InjectionModules {

    val modules = listOf(
        MainInjectionModule.module,
        ChartInjectionModule.module,
        NetworkInjectionModule.module
    )
}