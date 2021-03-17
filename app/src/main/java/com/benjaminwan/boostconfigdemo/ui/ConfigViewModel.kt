package com.benjaminwan.boostconfigdemo.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext

class ConfigViewModel(
    initialState: ConfigState,
) : MavericksViewModel<ConfigState>(initialState) {


    companion object : MavericksViewModelFactory<ConfigViewModel, ConfigState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ConfigState
        ): ConfigViewModel {
            return ConfigViewModel(state)
        }
    }
}