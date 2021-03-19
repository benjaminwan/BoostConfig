package com.benjaminwan.boostconfig.utils

import android.content.Context


/**
 * dp转px
 * @param  value dp值
 * @return 返回px值
 */
internal fun dp2px(context: Context, value: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (value.toFloat() * scale + 0.5f).toInt()
}