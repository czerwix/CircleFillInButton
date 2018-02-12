package com.mobeedev.circlefillinbutton.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Context.convertDpToPixels(dp: Float): Int = (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
fun Context.convertPixelsToDp(px: Float): Float = px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)