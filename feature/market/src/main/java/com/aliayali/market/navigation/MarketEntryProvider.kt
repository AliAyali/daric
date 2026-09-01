package com.aliayali.market.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.market.MarketRoute
import com.aliayali.navigation.Navigator

fun EntryProviderScope<NavKey>.marketEntry(navigator: Navigator) {
    entry<MarketNavKey> {
        MarketRoute()
    }
}