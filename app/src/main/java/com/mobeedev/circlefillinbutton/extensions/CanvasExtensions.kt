package com.mobeedev.circlefillinbutton.extensions

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF

inline fun <R> Canvas.use(func: Canvas.() -> R) {
    val state = save()
    func.invoke(this)
    restoreToCount(state)
}

inline fun Canvas.clip(left: Int, top: Int, right: Int, bottom: Int, block: Canvas.() -> Unit) = use {
    clipRect(left, top, right, bottom)
    block()
}

inline fun Canvas.clip(left: Float, top: Float, right: Float, bottom: Float, block: Canvas.() -> Unit) = use {
    clipRect(left, top, right, bottom)
    block()
}

inline fun Canvas.clip(rect: Rect, block: Canvas.() -> Unit) = use {
    clipRect(rect)
    block()
}

inline fun Canvas.clip(rect: RectF, block: Canvas.() -> Unit) = use {
    clipRect(rect)
    block()
}

inline fun Canvas.translate(dx: Float = 0F, dy: Float = 0F, block: Canvas.() -> Unit) = use {
    translate(dx, dy)
    block()
}