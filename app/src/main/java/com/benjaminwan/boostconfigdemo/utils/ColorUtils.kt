package com.benjaminwan.boostconfigdemo.utils

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColor(@ColorRes color: Int) =
    ContextCompat.getColor(requireContext(), color)