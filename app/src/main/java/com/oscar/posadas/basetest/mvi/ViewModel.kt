package com.oscar.posadas.basetest.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ViewModel<S, E : ViewEvent>(initialState: S) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    abstract fun processEvent(event: E)
}

