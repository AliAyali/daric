package com.aliayali.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val PullThreshold = 110f
private const val MaxPull = 180f

@Composable
fun DaricPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    isAtTop: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pullOffset = remember { Animatable(0f) }

    val connection = remember(isAtTop, isRefreshing) {
        object : NestedScrollConnection {

            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {

                if (
                    source == NestedScrollSource.UserInput &&
                    available.y > 0f &&
                    isAtTop &&
                    !isRefreshing
                ) {
                    val newOffset =
                        (pullOffset.value + available.y * 0.5f)
                            .coerceAtMost(MaxPull)

                    scope.launch {
                        pullOffset.snapTo(newOffset)
                    }

                    return Offset(
                        x = 0f,
                        y = available.y,
                    )
                }

                return Offset.Zero
            }

            override suspend fun onPreFling(
                available: Velocity,
            ): Velocity {
                if (pullOffset.value > 0f) {

                    if (
                        pullOffset.value >= PullThreshold &&
                        !isRefreshing
                    ) {
                        onRefresh()
                    }

                    pullOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow,
                        ),
                    )

                    return Velocity(
                        x = 0f,
                        y = available.y,
                    )
                }

                return Velocity.Zero
            }
        }
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            pullOffset.animateTo(
                targetValue = 56f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            )
        } else {
            pullOffset.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {

        if (pullOffset.value > 0f || isRefreshing) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        alpha = (pullOffset.value / PullThreshold)
                            .coerceIn(0f, 1f)
                    },
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = 0,
                        y = pullOffset.value.roundToInt(),
                    )
                }
                .nestedScroll(connection),
        ) {
            content()
        }
    }
}