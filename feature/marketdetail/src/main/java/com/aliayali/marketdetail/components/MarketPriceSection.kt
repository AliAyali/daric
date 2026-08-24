package com.aliayali.marketdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketPriceSection(
    data: MarketDetailUiData,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = data.formattedPrice,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = data.formattedChange,
            style = MaterialTheme.typography.titleMedium,
            color = if (data.isPositive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.error
            },
        )
    }
}