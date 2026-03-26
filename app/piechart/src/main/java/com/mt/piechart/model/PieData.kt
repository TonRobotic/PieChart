package com.mt.piechart.model

data class PieData(
    val title: String = "",
    val value: Float,
    val color: Int,
    var percentage: Float = 0f
)
