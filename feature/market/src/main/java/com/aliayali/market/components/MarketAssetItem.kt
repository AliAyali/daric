package com.aliayali.market.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.designsystem.icon.DaricIcons.ArrowDown
import com.aliayali.designsystem.icon.DaricIcons.ArrowUp
import com.aliayali.market.model.MarketItemUiModel

@Composable
fun MarketAssetItem(
    item: MarketItemUiModel.MarketAsset,
    onMarketAssetClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val changeColor =
        if (item.isPositive) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onMarketAssetClick(item.id)
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
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
            ) {

                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = if (item.isPositive) {
                        ArrowUp
                    } else {
                        ArrowDown
                    },
                    contentDescription = null,
                    tint = changeColor,
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = item.formattedChange,
                    color = changeColor,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Text(
                text = item.formattedPrice,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Column(
                    horizontalAlignment = Alignment.End,
                ) {

                    Text(
                        text = if (item.name.length > 13) {
                            "${item.name.take(15)}..."
                        } else {
                            item.name
                        },
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = item.symbol,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.onBackground
                                .copy(alpha = .10f),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = DaricIcons.MonetizationOn,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}