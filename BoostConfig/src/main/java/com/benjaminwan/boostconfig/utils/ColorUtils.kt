package com.benjaminwan.boostconfig.utils

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.benjaminwan.boostconfig.R
import com.benjaminwan.boostconfig.models.ColorStateRes

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

internal fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
    val a = Math.min(255, Math.max(0, (alpha * 255).toInt())) shl 24
    val rgb = 0x00ffffff and baseColor
    return a + rgb
}

internal fun getSwitchTrackColorStateList(context: Context): ColorStateList {
    return ColorStateRes(
        listOf(ColorStateRes.unChecked, ColorStateRes.checked, ColorStateRes.disabled),
        listOf(
            ContextCompat.getColor(context, R.color.material_grey_500),
            getColorWithAlpha(0.5f, getColorControlActivated(context)),
            ContextCompat.getColor(context, R.color.material_grey_200),
        )
    ).toColorStateList()
}

internal fun getSwitchThumbColorStateList(context: Context): ColorStateList {
    return ColorStateRes(
        listOf(ColorStateRes.unChecked, ColorStateRes.checked, ColorStateRes.disabled),
        listOf(
            ContextCompat.getColor(context, R.color.material_grey_200),
            getColorControlActivated(context),
            ContextCompat.getColor(context, R.color.material_grey_300),
        )
    ).toColorStateList()
}

internal fun getCheckBoxButtonColorStateList(context: Context): ColorStateList {
    return ColorStateRes(
        listOf(ColorStateRes.unChecked, ColorStateRes.checked, ColorStateRes.disabled),
        listOf(
            ContextCompat.getColor(context, R.color.material_grey_600),
            getColorControlActivated(context),
            ContextCompat.getColor(context, R.color.material_grey_300),
        )
    ).toColorStateList()
}

internal fun getColorControlActivated(context: Context): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.colorControlActivated, typedValue, true)
    return typedValue.data
}