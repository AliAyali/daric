package com.aliayali.marketdetail.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.marketdetail.MarketDetailRoute
import com.aliayali.navigation.Navigator

fun EntryProviderScope<NavKey>.marketDetailEntry(navigator: Navigator) {
    entry<MarketDetailNavKey> { key ->
        MarketDetailRoute(
            assetId = key.assetId,
        )
    }
}

fun Navigator.navigateToMarketDetail(
    topicId: String,
) {
    navigate(MarketDetailNavKey(topicId))
}