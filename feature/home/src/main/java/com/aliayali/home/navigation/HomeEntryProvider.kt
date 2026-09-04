package com.aliayali.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.home.HomeRoute
import com.aliayali.market.navigation.MarketNavKey
import com.aliayali.marketdetail.navigation.MarketDetailAssetType
import com.aliayali.marketdetail.navigation.navigateToMarketDetail
import com.aliayali.navigation.Navigator

fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator) {
    entry<HomeNavKey> {
        HomeRoute(
            onCoinClick = { assetId ->
                navigator.navigateToMarketDetail(
                    assetId = assetId,
                    assetType = MarketDetailAssetType.CRYPTO,
                )
            },
            onSectionMoreClick = { navigator.navigate(MarketNavKey) }
        )
    }
}