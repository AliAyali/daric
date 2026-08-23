package com.aliayali.home.components.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricOutlinedButton
import com.aliayali.home.R

@Composable
fun MarketListHeader(
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DaricOutlinedButton(
            onClick = onMoreClick,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        ) {

            Text(
                text = stringResource(R.string.feature_home_more),
                style = MaterialTheme.typography.labelMedium,
            )

        }
        Text(
            text = stringResource(R.string.feature_home_currency),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }

}