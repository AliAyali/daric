package com.aliayali.marketdetail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.marketdetail.R
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketInfoCard(
    data: MarketDetailUiData,
    modifier: Modifier = Modifier,
) {
    val changeColor = if (data.isPositive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = stringResource(R.string.feature_marketdetail_market_info_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MarketInfoItem(
                label = stringResource(R.string.feature_marketdetail_label_symbol),
                value = data.symbol.uppercase(),
                modifier = Modifier.weight(1f),
            )

            MarketInfoItem(
                label = stringResource(R.string.feature_marketdetail_label_price_usd),
                value = data.formattedDollarPrice ?: "-",
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MarketInfoItem(
                label = stringResource(R.string.feature_marketdetail_label_price_toman),
                value = data.formattedTomanPrice ?: "-",
                modifier = Modifier.weight(1f),
            )

            MarketInfoChangeItem(
                value = data.formattedChange,
                isPositive = data.isPositive,
                color = changeColor,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MarketInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.45f,
                ),
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp,
            ),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(6.dp))

        AnimatedContent(
            targetState = value,
            transitionSpec = {
                (
                        fadeIn() + scaleIn(
                            initialScale = 0.96f,
                        )
                        ).togetherWith(
                        fadeOut() + scaleOut(
                            targetScale = 0.96f,
                        ),
                    )
            },
            label = "market_info_value",
        ) { animatedValue ->
            Text(
                text = animatedValue,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun MarketInfoChangeItem(
    value: String,
    isPositive: Boolean,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color.copy(alpha = 0.08f),
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp,
            ),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = stringResource(R.string.feature_marketdetail_label_change_24h),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(6.dp))

        AnimatedContent(
            targetState = value,
            transitionSpec = {
                (
                        fadeIn() + scaleIn(
                            initialScale = 0.9f,
                        )
                        ).togetherWith(
                        fadeOut() + scaleOut(
                            targetScale = 0.9f,
                        ),
                    )
            },
            label = "market_change",
        ) { animatedValue ->
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        color.copy(alpha = 0.12f),
                    )
                    .padding(
                        horizontal = 9.dp,
                        vertical = 5.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = if (isPositive) {
                        DaricIcons.ArrowUp
                    } else {
                        DaricIcons.ArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = color,
                )

                Spacer(Modifier.size(4.dp))

                Text(
                    text = animatedValue,
                    style = MaterialTheme.typography.labelMedium,
                    color = color,
                )
            }
        }
    }
}