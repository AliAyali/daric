package com.aliayali.home.components.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.home.model.MarketSignalUiModel

@Composable
fun MarketSignals(
    signals: List<MarketSignalUiModel>,
    modifier: Modifier = Modifier,
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        signals.forEach { signal ->
            MarketSignalRow(signal)
        }
    }
}

@Composable
private fun MarketSignalRow(
    signal: MarketSignalUiModel,
) {
    val statusColor = when {
        signal.score > 0.1 ->
            MaterialTheme.colorScheme.primary

        signal.score < -0.1 ->
            MaterialTheme.colorScheme.error

        else ->
            MaterialTheme.colorScheme.tertiary
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(signal.titleRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
        )

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(7.dp)
                .clip(CircleShape)
                .background(statusColor),
        )
    }
}