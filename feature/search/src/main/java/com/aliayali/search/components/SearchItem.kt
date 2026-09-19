package com.aliayali.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aliayali.search.model.SearchItemUiModel

@Composable
fun SearchItem(
    item: SearchItemUiModel,
    onCoinClick: (id: String) -> Unit,
    onMarketAssetClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (item) {

        is SearchItemUiModel.Coin -> {
            SearchCoinItem(
                item = item,
                onCoinClick = onCoinClick,
                modifier = modifier,
            )
        }

        is SearchItemUiModel.MarketAsset -> {
            SearchMarketAssetItem(
                item = item,
                onMarketAssetClick = onMarketAssetClick,
                modifier = modifier,
            )
        }
    }
}