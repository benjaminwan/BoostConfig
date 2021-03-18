package com.benjaminwan.boostconfig.utils

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import com.benjaminwan.boostconfig.R


/**
 * dp转px
 * @param  value dp值
 * @return 返回px值
 */
internal fun dp2px(context: Context, value: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (value.toFloat() * scale + 0.5f).toInt()
}

internal fun getColorPrimary(context: Context): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}

internal fun getColorPrimaryVariant(context: Context): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true)
    return typedValue.data
}

internal fun getColorStateListPrimary(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_pressed, android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        ), intArrayOf(
            getColorPrimary(context), getColorPrimaryVariant(context), R.color.material_grey_300
        )
    )
}

internal fun getDarkColorPrimary(context: Context): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
    return typedValue.data
}