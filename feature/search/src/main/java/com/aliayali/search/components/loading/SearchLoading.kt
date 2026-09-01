package com.aliayali.search.components.loading

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.ShimmerBox

@Preview
@Composable
fun SearchLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .15f),
                shape = RoundedCornerShape(10.dp),
            ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ShimmerBox(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    shape = RoundedCornerShape(21.dp),
                )

                Spacer(
                    modifier = Modifier.width(12.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .width(75.dp)
                            .height(15.dp),
                    )

                    ShimmerBox(
                        modifier = Modifier
                            .width(42.dp)
                            .height(11.dp),
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {

                ShimmerBox(
                    modifier = Modifier
                        .width(90.dp)
                        .height(20.dp),
                )

                ShimmerBox(
                    modifier = Modifier
                        .width(65.dp)
                        .height(12.dp),
                )
            }

            ShimmerBox(
                modifier = Modifier
                    .width(62.dp)
                    .height(28.dp),
                shape = RoundedCornerShape(100.dp),
            )
        }
    }
}