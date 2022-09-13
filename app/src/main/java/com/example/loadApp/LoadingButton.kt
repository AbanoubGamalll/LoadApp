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


    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
    }

    private val paintText = Paint().apply {
        color = Color.WHITE
        textSize = 60.0f
        textAlign = Paint.Align.CENTER
    }

    private val paintCircle = Paint().apply { color = Color.YELLOW }
    private val paintRec = Paint().apply { color = Color.BLUE }

    private val Circle_PERCENTAGE_VALUE_HOLDER = "CirclePercentage"
    private val Rec_PERCENTAGE_VALUE_HOLDER = "RecPercentage"

    private var circleState = 0f
    private var recState = 0f
    private var text = "Download"
    private var clicked = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(context.getColor(R.color.green))

        if (circleState == 360f) {
            clicked = false
            text = "Download"
            isClickable = true
        }

        if (clicked) {
            canvas.drawRect(
                0f,
                0f,
                recState,//////
                height.toFloat(),
                paintRec
            )
            val xTC = ((width / 2) - ((paintText.descent() + paintText.ascent())) / 2) + 65 * 4
            val yTC = (height / 2).toFloat()
            canvas.drawArc(
                xTC + 10 - 30, yTC - 30,
                xTC + 40, yTC + 30,
                0F, circleState,
                true, paintCircle
            )
        }

        canvas.drawText(
            text,
            ((width / 2) - ((paintText.descent() + paintText.ascent())) / 2),
            ((height / 2) - ((paintText.descent() + paintText.ascent())) / 2),
            paintText
        )
    }


    private fun animateProgress() {

        isClickable = false
        clicked = true
        text = "We are loading"

        val circleValue = PropertyValuesHolder.ofFloat(
            Circle_PERCENTAGE_VALUE_HOLDER,
            0f,
            360f
        )
        val recValue = PropertyValuesHolder.ofFloat(
            Rec_PERCENTAGE_VALUE_HOLDER,
            0f,
            width.toFloat()
        )

        val animator = ValueAnimator().apply {
            setValues(circleValue, recValue)

            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener {
                circleState = it.getAnimatedValue(Circle_PERCENTAGE_VALUE_HOLDER) as Float
                recState = it.getAnimatedValue(Rec_PERCENTAGE_VALUE_HOLDER) as Float

                invalidate()
            }
        }

        animator.start()
    }


    override fun performClick(): Boolean {
        animateProgress()
        return super.performClick()
    }


}