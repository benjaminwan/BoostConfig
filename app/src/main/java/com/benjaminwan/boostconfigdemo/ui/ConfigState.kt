package com.benjaminwan.boostconfigdemo.ui

import com.airbnb.mvrx.MavericksState

data class ConfigState(
    val infoTotalCount: Int = 0
) : MavericksState
