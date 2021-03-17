package com.benjaminwan.boostconfigdemo.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.benjaminwan.boostconfigdemo.models.Student
import com.benjaminwan.boostconfigdemo.utils.copyAdd
import com.benjaminwan.boostconfigdemo.utils.copyAndSet

class ConfigViewModel(
    initialState: ConfigState,
) : MavericksViewModel<ConfigState>(initialState) {

    private val initStudents: List<Student> = listOf(
        Student.new("test1", 18, true),
        Student.new("test2", 18, true),
        Student.new("test3", 18, true),
    )

    fun initStudent() {
        setState {
            copy(students = initStudents)
        }
    }

    fun setStudent(student: Student) {
        setState {
            val oldStudents = this.students
            val index = oldStudents.indexOfFirst { it.id == student.id }
            val newStudents = if (index >= 0) oldStudents.copyAndSet(index, student) else oldStudents
            copy(students = newStudents)
        }
    }

    fun addStudent(student: Student) {
        setState {
            copy(students = this.students.copyAdd(student))
        }
    }


    companion object : MavericksViewModelFactory<ConfigViewModel, ConfigState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ConfigState
        ): ConfigViewModel {
            return ConfigViewModel(state)
        }
    }
}