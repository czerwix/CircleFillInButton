package com.mobeedev.circlefillinbutton.extensions

import android.content.res.TypedArray

inline fun TypedArray.use(block: TypedArray.() -> Unit) {
    this.block()
    recycle()
}