package com.benjaminwan.boostconfig.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.view.WindowManager
import androidx.fragment.app.Fragment

/**
 * dp转px
 * @param  value dp值
 * @return 返回px值
 */
internal fun dp2px(context: Context, value: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (value.toFloat() * scale + 0.5f).toInt()
}