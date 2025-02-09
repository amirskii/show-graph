package com.example.showgraph.di

import com.example.showgraph.data.remote.di.NetworkInjectionModule
import com.example.showgraph.presentation.di.MainInjectionModule


object InjectionModules {

    val modules = listOf(
        MainInjectionModule.module,
        NetworkInjectionModule.module
    )
}