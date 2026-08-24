package com.aliayali.marketdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aliayali.marketdetail.model.MarketDetailUiData

@Composable
fun MarketInfoCard(
    data: MarketDetailUiData,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Text(
                text = "Market Information",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Symbol",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    text = data.symbol.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}