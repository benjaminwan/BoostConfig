package com.benjaminwan.boostconfigdemo.models

import java.util.*

data class Student(
    val id: String,
    val name: String,
    val age: Int,
    val isMale: Boolean,
    val isGone: Boolean
) {
    companion object {
        fun new(name: String, age: Int, isMale: Boolean): Student {
            val uuid = UUID.randomUUID().toString()
            return Student(uuid, name, age, isMale, false)
        }
    }
}
