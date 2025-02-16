package com.example.biometricsample

import androidx.compose.ui.tooling.preview.Preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp

@Composable
fun DrawHalfCircle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val density = LocalDensity.current.density

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val borderSize = 20.dp.toPx() * density // Border width in pixels
            val halfCircleRadius = size.height / 2f - borderSize
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            drawIntoCanvas { canvas ->
                // Draw the fill (black color)
                canvas.drawCircle(Offset(centerX,centerY),centerX/2,Paint().apply {
                    style = PaintingStyle.Stroke
                    strokeWidth = borderSize
                    color = Color.Black
                })

                // Draw the border
//                canvas.drawRoundRect(
//                    0f,
//                            centerY - halfCircleRadius,
//                    size.width,
//                    centerY + halfCircleRadius,
//                    200.0f  ,200.0f     ,
//                    Paint().apply {
//                        style = PaintingStyle.Stroke
//                        strokeWidth = borderSize
//                        color = Color.White
//                    }
//                )
            }
        }
    }
}

@Composable
fun DrawColoredBorderCircle() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val density = LocalDensity.current.density

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val borderSize = 20.dp.toPx() * density // Border width in pixels
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = centerX - borderSize

            drawIntoCanvas { canvas ->
                val totalPerimeter = 2 * Math.PI * radius
                val blackPercentage = 0.5 // 50% with black
                val blackPerimeter = totalPerimeter * blackPercentage

                // Draw the black portion
                canvas.drawArc(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius,
                    180f,
                    360f * (blackPerimeter / totalPerimeter).toFloat(),
                    false,
                    Paint().apply {
                        style =PaintingStyle.Stroke
                        strokeWidth = borderSize
                        color = Color.Black
                    }
                )

                //Draw the green portion
                canvas.drawArc(
                    centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius,
                    0f,
                    180f,
                    false,
                    Paint().apply {
                        style = PaintingStyle.Stroke
                        strokeWidth = borderSize
                        color = Color.Green
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawHallfCirclePreview(){
    DrawHalfCircle()
}
