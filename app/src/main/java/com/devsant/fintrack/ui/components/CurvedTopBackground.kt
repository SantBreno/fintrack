package com.devsant.fintrack.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun CurvedTopBackground(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, height * 0.7f)

            quadraticTo(width / 2, height, width, height * 0.7f)
            lineTo(width, 0f)
            close()
        }

        drawPath(
            path = path,
            color = color
        )
    }
}