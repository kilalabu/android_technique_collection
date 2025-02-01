package com.example.android_technique_collection.feature.chart.state

import androidx.compose.ui.graphics.Color

data class PieChartsElement(
    val pieDegrees: Float,
    val color: Color
) {
    companion object {
        fun fake(): List<PieChartsElement> {
            fun calculatePieDegrees(ratio: Float): Float {
                return 360f * ratio / 100f
            }

            return listOf(
                PieChartsElement(
                    pieDegrees = calculatePieDegrees(50f),
                    color = Color.Blue
                ),
                PieChartsElement(
                    pieDegrees = calculatePieDegrees(25f),
                    color = Color.Green
                ),
                PieChartsElement(
                    pieDegrees = calculatePieDegrees(15f),
                    color = Color.Yellow
                ),
                PieChartsElement(
                    pieDegrees = calculatePieDegrees(10f),
                    color = Color.Red
                ),
            )
        }
    }
}
