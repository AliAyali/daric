package com.aliayali.home.components.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.home.R
import com.aliayali.home.mapper.asStringRes
import com.aliayali.home.model.MarketSignalUiModel
import com.aliayali.model.analysis.MarketReason

@Composable
fun MarketAnalysisDetails(
    signals: List<MarketSignalUiModel>,
    reasons: List<MarketReason>,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.06f),
                )
                .clickable {
                    expanded = !expanded
                }
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background.copy(
                    alpha = 0.8f,
                )
            )

            Text(
                text = stringResource(
                    if (expanded) {
                        R.string.feature_home_market_analysis_close_details
                    } else {
                        R.string.feature_home_market_analysis_show_details
                    }
                ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.background.copy(
                    alpha = 0.8f,
                ),
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.06f)
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 10.dp,
                    ),

                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                MarketSignals(
                    signals = signals,
                )

                if (reasons.isNotEmpty()) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.background.copy(
                            alpha = 0.08f,
                        ),
                    )

                    reasons
                        .take(3)
                        .forEach { reason ->
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(reason.asStringRes()),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background.copy(
                                    alpha = 0.65f,
                                ),
                            )
                        }
                }
            }
        }
    }
}