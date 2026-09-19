package com.aliayali.marketdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.ShimmerBox

@Preview
@Composable
fun MarketDetailSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            top = 32.dp,
            end = 16.dp,
            start = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.15f,
                    ),
                    shape = RoundedCornerShape(24.dp),
                ),
            shape = RoundedCornerShape(24.dp),
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.15f,
                    ),
                    shape = RoundedCornerShape(24.dp),
                ),
            shape = RoundedCornerShape(24.dp),
        )

        ShimmerBox(
            modifier = Modifier
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

        ShimmerBox(
            modifier = Modifier
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
}