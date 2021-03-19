package com.benjaminwan.boostconfig.models

import android.content.res.ColorStateList

data class ColorStateRes(
    val states: List<IntArray> = emptyList(),
    val colors: List<Int> = emptyList()
) {
    companion object {
        val unPressed = intArrayOf(
            -android.R.attr.state_pressed,
            android.R.attr.state_enabled
        )
        val pressed = intArrayOf(
            android.R.attr.state_pressed,
            android.R.attr.state_enabled
        )
        val disabled = intArrayOf(
            -android.R.attr.state_enabled
        )
        val unChecked = intArrayOf(
            -android.R.attr.state_checked,
            android.R.attr.state_enabled
        )
        val checked = intArrayOf(
            android.R.attr.state_checked,
            android.R.attr.state_enabled
        )
    }

    fun toColorStateList(): ColorStateList {
        val statesArray = states.toTypedArray()
        return ColorStateList(statesArray, colors.toIntArray())
    }
}
