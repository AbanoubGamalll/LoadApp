package com.example.loadApp

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates
import android.view.animation.AccelerateDecelerateInterpolator

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0


    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
    }

    private val paintText = Paint().apply {
        color = Color.WHITE
        textSize = 60.0f
        textAlign = Paint.Align.CENTER
    }

    private val paintCircle = Paint().apply { color = Color.YELLOW }
    private val paintRec = Paint().apply { color = Color.BLUE }

    private val PERCENTAGE_VALUE_HOLDER = "percentage"
    private var circleState = 0f
    private var text = "Download"
    private var clicked = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(context.getColor(R.color.green))
        if (circleState == 360f) {
            clicked = false
            text = "Download"
        }
        var xTC = ((width / 2) - ((paintText.descent() + paintText.ascent())) / 2)
        var yTC = ((height / 2) - ((paintText.descent() + paintText.ascent())) / 2)
        canvas.drawText(text, xTC, yTC, paintText)

        if (clicked) {
            xTC += 65 * 4
            yTC = (height / 2).toFloat()

            canvas.drawArc(
                xTC + 10 - 30, yTC - 30,
                xTC + 40, yTC + 30,
                0F, circleState,
                true, paintCircle
            )
            //canvas.drawRect()
        }
    }


    private fun animateProgress() {
        clicked = true
        text = "We are loading"
        val valuesHolder = PropertyValuesHolder.ofFloat(
            PERCENTAGE_VALUE_HOLDER,
            0f,
            360f
        )

        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener {
                circleState = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                //buton state

                invalidate()
            }
        }
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minW, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun performClick(): Boolean {
        animateProgress()
        return super.performClick()
    }


}