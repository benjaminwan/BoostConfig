package com.benjaminwan.boostconfigdemo.ui

import com.airbnb.mvrx.MavericksState
import com.benjaminwan.boostconfigdemo.models.Student

data class ConfigState(
    val students: List<Student> = (0..100).map {
        Student.new("student$it", 0, it % 2 == 0)
    }
) : MavericksState
