package com.aliayali.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.aliayali.home.model.MarketStatus
import com.aliayali.home.model.MarketStatusTone

@Composable
fun MarketStatusRing(
    status: MarketStatus,
    modifier: Modifier = Modifier,
) {
    val ringBackgroundColor = MaterialTheme.colorScheme.background.copy(alpha = 0.08f)
    val statusColor = when (status.tone) {
        MarketStatusTone.Success -> MaterialTheme.colorScheme.primary
        MarketStatusTone.Normal -> MaterialTheme.colorScheme.tertiary
        MarketStatusTone.Warning -> MaterialTheme.colorScheme.onErrorContainer
        MarketStatusTone.Error -> MaterialTheme.colorScheme.error
    }
    val animatedProgress by animateFloatAsState(
        targetValue = status.progress,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing,
        ),
        label = "marketRingAnimation",
    )

    Box(
        modifier = modifier.size(48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            statusColor.copy(alpha = .20f),
                            Color.Transparent,
                        )
                    ),
                    shape = CircleShape
                )
        ) {
            Canvas(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
            ) {

                val stroke = 2.dp.toPx()

                drawArc(
                    color = ringBackgroundColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round,
                    )
                )

                drawArc(
                    color = statusColor,
                    startAngle = -90f,
                    sweepAngle = 360 * animatedProgress,
                    useCenter = false,
                    style = Stroke(
                        width = stroke,
                        cap = StrokeCap.Round,
                    )
                )

            }
        }

        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(statusColor)
        )

    }

}