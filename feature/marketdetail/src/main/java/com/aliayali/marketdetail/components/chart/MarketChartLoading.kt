package com.aliayali.marketdetail.components.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.ShimmerBox

@Composable
fun MarketChartLoading(
    modifier: Modifier = Modifier,
) {
    ShimmerBox(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.15f,
                ),
                shape = RoundedCornerShape(24.dp),
            ),
        shape = RoundedCornerShape(24.dp),
    )
}