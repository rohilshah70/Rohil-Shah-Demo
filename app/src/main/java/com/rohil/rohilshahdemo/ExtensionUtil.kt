package com.rohil.rohilshahdemo

import android.graphics.BlurMaskFilter
import android.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @created 13/09/25 - 21:29
 * @project Zoomcar
 * @author Rohil
 * Copyright (c) 2024 Zoomcar. All rights reserved.
 */

enum class CustomShadowType {
    LOW,
    HIGH_INVERSE;
}

fun Modifier.customShadow(
    type: CustomShadowType,
    roundRadius: Dp = Dp.Hairline
) = drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val blurRadius = when (type) {
            CustomShadowType.LOW -> 2.dp
            CustomShadowType.HIGH_INVERSE -> 8.dp
        }
        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        var leftPixel = 0f
        var topPixel = 0f
        var rightPixel = 0f
        var bottomPixel = 0f

        when (type) {
            CustomShadowType.LOW -> {
                frameworkPaint.color = Color.LTGRAY
                leftPixel = 1.dp.unaryMinus().toPx()
                topPixel = 2.dp.toPx()
                rightPixel = size.width - leftPixel
                bottomPixel = size.height + topPixel
            }

            CustomShadowType.HIGH_INVERSE -> {
                frameworkPaint.color =  Color.LTGRAY
                leftPixel = Dp.Hairline.toPx()
                bottomPixel = 6.dp.unaryMinus().toPx()
                rightPixel = size.width - leftPixel
                topPixel = size.height + bottomPixel
            }
        }

        canvas.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            paint = paint,
            radiusX = roundRadius.toPx(),
            radiusY = roundRadius.toPx()
        )
    }
}