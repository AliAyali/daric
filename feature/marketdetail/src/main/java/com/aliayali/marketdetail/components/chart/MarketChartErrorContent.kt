package com.aliayali.marketdetail.components.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricButton
import com.aliayali.marketdetail.R

@Composable
fun MarketChartErrorContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(
                    R.string.feature_marketdetail_chart_error_title,
                ),
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(
                modifier = Modifier.height(8.dp),
            )

            Text(
                text = stringResource(
                    R.string.feature_marketdetail_chart_error_description,
                ),
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            DaricButton(
                onClick = onRetry,
            ) {
                Text(
                    text = stringResource(
                        R.string.feature_marketdetail_error_retry,
                    ),
                )
            }
        }
    }
}