package com.aliayali.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.home.R

@Composable
fun MarketOverviewHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background.copy(.08f),
                    RoundedCornerShape(50)
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            LiveIndicator()

            Spacer(Modifier.width(6.dp))

            Text(
                text = stringResource(R.string.feature_home_market_live),
                color = MaterialTheme.colorScheme.background.copy(.8f),
                style = MaterialTheme.typography.labelMedium,
            )

        }
        Text(
            text = stringResource(R.string.feature_home_market_summary_today),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.background.copy(.55f),
        )
    }
}