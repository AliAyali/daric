package com.aliayali.home.components.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.home.R
import com.aliayali.home.model.MarketSectionCardUiModel
import com.aliayali.model.data.MarketCategory

@Composable
fun MarketSection(
    section: MarketSectionCardUiModel,
    onCoinClick: (id: String) -> Unit,
    onMoreClick: () -> Unit,
) {
    val title = when (section.category) {
        MarketCategory.Crypto -> stringResource(R.string.feature_home_crypto)
        MarketCategory.Currency -> stringResource(R.string.feature_home_currency)
        MarketCategory.Gold -> stringResource(R.string.feature_home_gold)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        MarketSectionHeader(
            title = title,
            itemCount = section.items.size,
            onMoreClick = onMoreClick
        )

        section.items.forEach { item ->
            MarketAssetItem(
                item = item,
                onCoinClick = onCoinClick
            )
        }
    }

}