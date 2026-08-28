package com.aliayali.marketdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aliayali.marketdetail.R
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketInfoCard(
    data: MarketDetailUiData,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 8.dp,
            )
        ) {
            MarketInfoRow(
                label = stringResource(
                    R.string.feature_marketdetail_label_symbol,
                ),
                value = data.symbol.uppercase(),
            )

            MarketInfoDivider()

            MarketInfoRow(
                label = stringResource(
                    R.string.feature_marketdetail_label_price_usd,
                ),
                value = data.formattedDollarPrice ?: "-",
                valueStyle = MaterialTheme.typography.titleMedium,
            )

            MarketInfoDivider()

            MarketInfoRow(
                label = stringResource(
                    R.string.feature_marketdetail_label_price_toman,
                ),
                value = data.formattedTomanPrice ?: "-",
                valueStyle = MaterialTheme.typography.titleMedium,
            )

            MarketInfoDivider()

            MarketInfoRow(
                label = stringResource(
                    R.string.feature_marketdetail_label_change_24h,
                ),
                value = data.formattedChange,
                valueStyle = MaterialTheme.typography.titleMedium,
                valueColor = when {
                    data.isPositive -> MaterialTheme.colorScheme.primary
                    !data.isPositive -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                },
            )
        }
    }
}

@Composable
private fun MarketInfoDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(
            alpha = 0.5f,
        ),
    )
}

@Composable
private fun MarketInfoRow(
    label: String,
    value: String,
    valueStyle: TextStyle = MaterialTheme.typography.titleSmall,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value,
            style = valueStyle,
            color = valueColor,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}