package com.mt.piechartm

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.withStyledAttributes
import com.mt.piechart.model.PieData

/**
 * TODO: document your custom view class.
 */
class PieChart : View, ValueAnimator.AnimatorUpdateListener {

    private var pieDataList = mutableListOf<PieData>()
    private var borderProgressBarSize: Float = 0f
    private var scaleColor: Int? = null
    private var updateValueAnimate = 1
    private var startPoint = 0f
    private var isRevert = true

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.withStyledAttributes(
            attrs, R.styleable.PieChart, defStyle, 0
        ) {

            borderProgressBarSize =
                getDimensionPixelSize(R.styleable.PieChart_borderPieSize, 120)
                    .toFloat()
            scaleColor = getColor(R.styleable.PieChart_scaleColor, Color.GRAY)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = (this.width / 2) - (borderProgressBarSize / 2)
        var totalValue = 0f
        val rectF = RectF(
            this.width.toFloat() / 2 - radius,
            this.height.toFloat() / 2 - radius,
            this.width.toFloat() / 2 + radius,
            this.height.toFloat() / 2 + radius
        )
        for (it in this.pieDataList) {
            val paint = Paint()
            paint.color = it.color
            paint.strokeWidth = borderProgressBarSize
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.BUTT

            val valueProgress =
                if (this.isRevert) percentTORadiusValue(it.percentage) * (-1) else percentTORadiusValue(
                    it.percentage
                )

            canvas.drawArc(
               rectF, startPoint + totalValue, valueProgress, false, paint
            )

            totalValue += valueProgress
        }
        // default
        if (this.pieDataList.isEmpty()){
            val paint = Paint()
            paint.color = scaleColor ?: Color.GRAY
            paint.strokeWidth = borderProgressBarSize
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.BUTT

            canvas.drawCircle(
                (this.width / 2).toFloat(),
                (this.height / 2).toFloat(), radius, paint
            )
        }
    }

    private fun percentTORadiusValue(progressValue: Float): Float {
        return ((progressValue * 360 / 100))
    }

    private fun calculateValuePercentage(
        pieDataList: List<PieData>
    ): List<PieData> {
        val totalValue = pieDataList.sumOf { it.value.toDouble() }
        pieDataList.forEach {
            it.percentage = ((it.value * this.updateValueAnimate) / totalValue).toFloat()
        }
        return pieDataList
    }

    private fun updateValue(value: Int) {
        this.updateValueAnimate = value
        if (this.updateValueAnimate > 0) {
            this.pieDataList = calculateValuePercentage(this.pieDataList) as MutableList<PieData>
            this.invalidate()
        }
    }

    fun setPieValue(
        pieDataList: List<PieData>,
        isAnimate: Boolean = false,
        delay: Long = 500,
        isRevert: Boolean = false,
        startPoint: Float = 90f /* 0f - 360f */
    ) {
        this.pieDataList = pieDataList as MutableList<PieData>
        this.isRevert = isRevert
        this.startPoint = startPoint
        if (isAnimate) {
            setPieWithAnimation(delay)
        } else {
            this.updateValue(100)
        }
    }

    @SuppressLint("Recycle")
    private fun setPieWithAnimation(delay: Long) {
        ValueAnimator.ofInt(0, 100).apply {
            duration = delay
            addUpdateListener(this@PieChart)
        }.start()
    }

    override fun onAnimationUpdate(p0: ValueAnimator) {
        val value = p0.animatedValue as Int
        Log.d("TAG", "onAnimationUpdate: $value")
        updateValue(value)
    }

}