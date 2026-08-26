package com.aliayali.marketdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricAsyncImage
import com.aliayali.designsystem.icon.DaricIcons.ArrowDown
import com.aliayali.designsystem.icon.DaricIcons.ArrowUp
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketAssetHeader(
    data: MarketDetailUiData,
    modifier: Modifier = Modifier,
) {
    val changeColor =
        if (data.isPositive)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.error

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = .10f)),
                contentAlignment = Alignment.Center,
            ) {

                DaricAsyncImage(
                    imageUrl = data.imageUrl,
                    contentDescription = null,
                    modifier = modifier
                )

            }

            Spacer(Modifier.width(12.dp))

            Column {

                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = data.symbol,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

            }

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            data.formattedDollarPrice?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(Modifier.height(2.dp))

            data.formattedTomanPrice?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

        }

        Row(
            modifier = Modifier
                .background(
                    color = changeColor.copy(alpha = .12f),
                    shape = RoundedCornerShape(100),
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = if (data.isPositive) ArrowUp else ArrowDown,
                contentDescription = null,
                tint = changeColor,
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = data.formattedChange,
                color = changeColor,
                style = MaterialTheme.typography.labelMedium,
            )

        }

    }

}