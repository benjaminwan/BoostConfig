package com.benjaminwan.boostconfigdemo.utils

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

inline val <reified S : MavericksState>MavericksViewModel<S>.current: S
    get() = runBlocking(Dispatchers.IO) {
        this@current.awaitState()
    }