package com.mt.mpiechart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mt.piechart.PieChart
import com.mt.piechart.model.PieData

class MainActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // define pie chart //
        pieChart = findViewById(R.id.pieChart)

        // mock pie model //
        val pieDataList = listOf(
            PieData(title = "Python", value = 10000f, color = getColor(android.R.color.holo_blue_dark)),
            PieData(title = "Java", value = 2000f, color = getColor(android.R.color.holo_green_dark)),
            PieData(title = "Kotlin", value = 5000f, color = getColor(com.mt.piechart.R.color.gray_600)),
            PieData(title = "JavaScript", value = 8500f, color = getColor(android.R.color.holo_red_dark)),
            PieData(title = "PHP", value = 4000f, color = getColor(android.R.color.holo_green_light)),
        )

        // set pie chart //
        pieChart.setPieValue(pieDataList, isAnimate = true, delay = 1000, isRevert = true, startPoint = 135f)
    }
}