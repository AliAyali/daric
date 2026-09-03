package com.aliayali.market.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.market.MarketRoute
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.navigateToMarketDetail
import com.aliayali.navigation.Navigator

fun EntryProviderScope<NavKey>.marketEntry(navigator: Navigator) {
    entry<MarketNavKey> {
        MarketRoute(
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