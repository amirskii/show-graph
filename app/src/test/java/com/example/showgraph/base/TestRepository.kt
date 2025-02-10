package com.example.showgraph.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class TestRepository<T> {
    private val _flow = MutableSharedFlow<T>()
    val flow: SharedFlow<T> = _flow

    suspend fun emit(value: T) {
        _flow.emit(value)
    }
}