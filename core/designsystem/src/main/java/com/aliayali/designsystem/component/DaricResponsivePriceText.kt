package com.aliayali.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp

@Composable
fun DaricResponsivePriceText(
    price: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    val textMeasurer = rememberTextMeasurer()

    androidx.compose.foundation.layout.BoxWithConstraints(
        modifier = modifier,
    ) {
        val maxFontSize = style.fontSize
        val minFontSize = 12.sp

        val fontSize = generateSequence(maxFontSize) { current ->
            (current.value - 1f).sp
        }
            .takeWhile { it >= minFontSize }
            .firstOrNull { size ->
                val result = textMeasurer.measure(
                    text = price,
                    style = style.copy(
                        fontSize = size,
                    ),
                    maxLines = 1,
                )

                result.size.width <= constraints.maxWidth
            }
            ?: minFontSize

        Text(
            text = price,
            modifier = Modifier,
            style = style.copy(
                fontSize = fontSize,
            ),
            color = color,
            maxLines = 1,
            softWrap = false,
        )
    }
}