package com.aliayali.search.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.navigateToMarketDetail
import com.aliayali.navigation.Navigator
import com.aliayali.search.SearchRoute

fun EntryProviderScope<NavKey>.searchEntry(navigator: Navigator) {
    entry<SearchNavKey> {
        SearchRoute(
            onBackClick = { navigator.goBack() },
            onCoinClick = { assetId ->
                navigator.navigateToMarketDetail(
                    assetId = assetId,
                    assetType = MarketDetailAssetType.CRYPTO,
                )
            },
            onMarketAssetClick = { assetId ->
                navigator.navigateToMarketDetail(
                    assetId = assetId,
                    assetType = MarketDetailAssetType.MARKET,
                )
            },
        )
    }
}