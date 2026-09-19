package com.aliayali.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.ShimmerBox

@Composable
fun NewsItemShimmer(
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
    ) {

        ShimmerBox(
            modifier = Modifier
                .size(110.dp)
                .clip(shape),
            shape = shape,
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(14.dp),
                shape = RoundedCornerShape(6.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                shape = RoundedCornerShape(6.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(18.dp),
                shape = RoundedCornerShape(6.dp),
            )

            Spacer(
                modifier = Modifier.height(2.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp),
                shape = RoundedCornerShape(6.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(14.dp),
                shape = RoundedCornerShape(6.dp),
            )

            Spacer(
                modifier = Modifier.height(2.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(12.dp),
                shape = RoundedCornerShape(6.dp),
            )
        }
    }
}