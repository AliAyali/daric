package com.aliayali.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.aliayali.home.model.MarketSectionCardUiModel

@Composable
fun MarketSection(
    section: MarketSectionCardUiModel,
    onCoinClick: (id: String) -> Unit,
    onMoreClick: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        MarketSectionHeader(
            title = section.title,
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