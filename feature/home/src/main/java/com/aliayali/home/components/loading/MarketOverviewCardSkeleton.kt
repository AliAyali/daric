package com.aliayali.home.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.ShimmerBox

@Composable
fun MarketOverviewCardSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .15f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ShimmerBox(
                modifier = Modifier
                    .width(30.dp)
                    .height(18.dp),
                shape = RoundedCornerShape(12.dp),
            )
            ShimmerBox(
                modifier = Modifier
                    .width(60.dp)
                    .height(18.dp),
                shape = RoundedCornerShape(12.dp),
            )
        }

        Spacer(
            modifier = Modifier.height(28.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            ShimmerBox(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(24.dp),
            )

            Spacer(
                modifier = Modifier.width(18.dp),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalAlignment = Alignment.End
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(18.dp),
                )

                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp),
                )

                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(14.dp),
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp),
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(
            modifier = Modifier.height(16.dp),
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.background.copy(
                alpha = 0.08f,
            ),
        )

        Spacer(
            modifier = Modifier.height(20.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            MarketPriceCardSkeleton(
                modifier = Modifier.weight(1f),
            )

            MarketPriceCardSkeleton(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MarketPriceCardSkeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {

        ShimmerBox(
            modifier = Modifier
                .width(70.dp)
                .height(14.dp),
        )

        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
        )

        ShimmerBox(
            modifier = Modifier
                .width(55.dp)
                .height(13.dp),
        )
    }
}