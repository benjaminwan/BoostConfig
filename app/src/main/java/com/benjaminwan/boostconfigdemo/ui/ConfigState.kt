package com.benjaminwan.boostconfigdemo.ui

import com.airbnb.mvrx.MavericksState
import com.benjaminwan.boostconfigdemo.models.Student

data class ConfigState(
    val students: List<Student> = (0..50).map {
        Student.new("student$it", 100, it % 2 == 0)
    }
) : MavericksState {
    fun getStudent(id: String):Student {
        return students.first { it.id == id }
    }
}
