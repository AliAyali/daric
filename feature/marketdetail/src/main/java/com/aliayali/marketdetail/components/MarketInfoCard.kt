package com.aliayali.marketdetail.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            MarketInfoRow(
                label = stringResource(id = R.string.feature_marketdetail_label_symbol),
                value = data.symbol.uppercase()
            )
            HorizontalDivider(
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
            )
            MarketInfoRow(
                label = stringResource(id = R.string.feature_marketdetail_label_price_usd),
                value = data.formattedDollarPrice ?: "-"
            )
            HorizontalDivider(
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
            )
            MarketInfoRow(
                label = stringResource(id = R.string.feature_marketdetail_label_price_toman),
                value = data.formattedTomanPrice ?: "-"
            )
            HorizontalDivider(
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
            )
            MarketInfoRow(
                label = stringResource(id = R.string.feature_marketdetail_label_change_24h),
                value = data.formattedChange
            )
        }
    }
}

@Composable
fun MarketInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}