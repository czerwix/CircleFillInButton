package com.mobeedev.circlefillinbutton

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.mobeedev.circlefillinbutton.extensions.use

class CircleFillInImageButton : CircleFillInBase {
    private lateinit var _drawable: Drawable

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, VIEW_DEFAULT_STYLE)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(attrs, defStyle)
        setOnClickListener(this)
    }

    private fun initView(attrs: AttributeSet, defStyle: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.circle_fill_in_button, defStyle, VIEW_DEFAULT_STYLE_RESOURCE).use {

            fillStages = getInteger(R.styleable.circle_fill_in_button_numberOfStages, fillStages)
            if (fillStages < 1) throw IllegalStateException("FIll in stages can not be less then 1")

            _drawable = getDrawable(R.styleable.circle_fill_in_button_imageViewResource) ?: context.resources.getDrawable(R.drawable.blue_bee_icon)

            val textViewColor = getColor(R.styleable.circle_fill_in_button_subtextColor, context.resources.getColor(R.color.black))
            val textViewSize = getDimension(R.styleable.circle_fill_in_button_subtextSize, DEFAULT_TEXT_SIZE)
            val textViewText = getString(R.styleable.circle_fill_in_button_subtextText)

            descriptionTextView = TextView(context).apply {
                text = textViewText
                setTextColor(textViewColor)
                textSize = textViewSize
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }

    override fun drawCircleLabel(canvas: Canvas) {
        val bitmap = (_drawable as BitmapDrawable).bitmap
        val left = circleClip.centerX() - bitmap.width / 2F
        val top = circleClip.centerY() - bitmap.height / 2F

        canvas.drawBitmap(bitmap, left, top, Paint())
    }
}