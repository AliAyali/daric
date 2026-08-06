package com.aliayali.home.components.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricOutlinedButton
import com.aliayali.home.R

@Composable
fun MarketSectionHeader(
    title: String,
    itemCount: Int,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DaricOutlinedButton(
            onClick = onMoreClick,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        ) {

            Text(
                text = stringResource(R.string.feature_home_more),
                style = MaterialTheme.typography.labelMedium,
            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = .12f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 3.dp,
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = itemCount.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}