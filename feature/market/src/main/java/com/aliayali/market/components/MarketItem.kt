package com.aliayali.market.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aliayali.market.model.MarketItemUiModel

@Composable
fun MarketItem(
    item: MarketItemUiModel,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (item) {

        is MarketItemUiModel.Coin -> {
            MarketCoinItem(
                item = item,
                onCoinClick = onCoinClick,
                modifier = modifier,
            )
        }

        is MarketItemUiModel.MarketAsset -> {
            MarketAssetItem(
                item = item,
                onMarketAssetClick = onMarketAssetClick,
                modifier = modifier,
            )
        }
    }
}