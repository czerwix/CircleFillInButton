package com.mobeedev.circlefillinbutton


import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.mobeedev.circlefillinbutton.extensions.clip
import com.mobeedev.circlefillinbutton.extensions.convertDpToPixels
import com.mobeedev.circlefillinbutton.extensions.translate

const val VIEW_DEFAULT_STYLE_RESOURCE = 0
const val VIEW_DEFAULT_STYLE = 0

const val DEFAULT_TEXT_SIZE = 16F
const val DEFAULT_TEXT_TOP_PADDING = 8F
const val CIRCLE_RADIUS = 32F

abstract class CircleFillInBase(context: Context, attrs: AttributeSet, defStyle: Int) : View(context, attrs, defStyle), View.OnClickListener {
    open var fillStages = 1
        protected set
    var currentStage = 0
        set(value) {
            field = value
            invalidate()
        }

    protected lateinit var circleBackground: Drawable
    protected lateinit var circleForeground: Drawable
    protected lateinit var descriptionTextView: TextView

    protected val circleRadius = context.convertDpToPixels(CIRCLE_RADIUS)
    protected val textPadding = context.convertDpToPixels(DEFAULT_TEXT_TOP_PADDING)
    protected val fillInHeight
        get () = (circleRadius * 2) / fillStages * currentStage.toFloat()
    protected val circleClip
        get() = RectF(width / 2F - circleRadius, paddingTop.toFloat(), width / 2F + circleRadius, paddingTop + 2F * circleRadius)
    protected val descriptionPosition
        get() = paddingTop + textPadding + circleRadius * 2F


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = circleRadius.toInt() + paddingLeft + paddingRight
        val height = circleRadius.toInt() + paddingTop + paddingBottom

        val widthFinal = resolveSizeAndState(width, widthMeasureSpec, 0)
        val heightFinal = resolveSizeAndState(height, heightMeasureSpec, 0)

        setMeasuredDimension(widthFinal, heightFinal)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawText(canvas)
        canvas.clip(circleClip) {
            drawBackgroundCircle(this)
            drawActiveCircle(this)
            drawCircleLabel(this)
        }
    }

    private fun drawBackgroundCircle(canvas: Canvas) {
        circleBackground = context.getDrawable(R.drawable.circle_fill_in_button_background)

        circleBackground.bounds = canvas.clipBounds
        circleBackground.draw(canvas)
    }

    private fun drawActiveCircle(canvas: Canvas) {
        if (currentStage == 0) return

        circleForeground = context.getDrawable(R.drawable.circle_fill_in_button_foreground)
        circleForeground.bounds = canvas.clipBounds
        val top = (paddingTop + circleRadius * 2 - fillInHeight).toInt()

        canvas.clip(0, top, width, height) {
            circleForeground.draw(this)
        }
    }

    abstract fun drawCircleLabel(canvas: Canvas)

    private fun drawText(canvas: Canvas) {
        descriptionTextView.measure(MeasureSpec.makeMeasureSpec(canvas.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(canvas.height, MeasureSpec.EXACTLY))
        descriptionTextView.layout(0, 0, descriptionTextView.measuredWidth, descriptionTextView.measuredHeight)

        canvas.translate(dy = descriptionPosition) {
            descriptionTextView.draw(this)
        }
    }

    override fun onClick(v: View?) {
        currentStage = if (currentStage >= fillStages) 0 else currentStage.inc()
        invalidate()
    }
}