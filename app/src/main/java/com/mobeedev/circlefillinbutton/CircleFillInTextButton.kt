package com.mobeedev.circlefillinbutton

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.mobeedev.circlefillinbutton.extensions.use

private const val DEFAULT_TEXT_HOUR_SIZE = 14F

class CircleFillInTextButton : CircleFillInBase {
    private var _buttonTexts = mutableListOf(context.resources.getString(R.string.mobeedev), context.resources.getString(R.string.mobeedev))
    private lateinit var _hourTextView: TextView

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, VIEW_DEFAULT_STYLE)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(attrs, defStyle)
        fillStages = 1
        setOnClickListener(this)
    }

    private fun initView(attrs: AttributeSet, defStyle: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.circle_fill_in_button, defStyle, VIEW_DEFAULT_STYLE_RESOURCE).use {

            val customEntries = getTextArray(R.styleable.circle_fill_in_button_textEntries)
            if (customEntries != null && customEntries.isNotEmpty()) customEntries.forEach { _buttonTexts.add(it.toString()) }

            descriptionTextView = TextView(context).apply {
                text = getString(R.styleable.circle_fill_in_button_subtextText)
                setTextColor(getColor(R.styleable.circle_fill_in_button_subtextColor, context.resources.getColor(R.color.yellow)))
                textSize = getDimension(R.styleable.circle_fill_in_button_subtextSize, DEFAULT_TEXT_SIZE)
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            _hourTextView = TextView(context).apply {
                text = _buttonTexts[currentStage]
                setTextColor(resources.getColor(R.color.white))
                textSize = DEFAULT_TEXT_HOUR_SIZE
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER
            }
        }
    }

    override fun drawCircleLabel(canvas: Canvas) {
        _hourTextView.text = _buttonTexts[currentStage]
        _hourTextView.measure(MeasureSpec.makeMeasureSpec(canvas.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(canvas.clipBounds.height(), MeasureSpec.EXACTLY))
        _hourTextView.layout(0, 0, _hourTextView.measuredWidth, _hourTextView.measuredHeight)

        _hourTextView.draw(canvas)
    }
}