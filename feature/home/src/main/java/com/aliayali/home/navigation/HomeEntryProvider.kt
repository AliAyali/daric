package com.aliayali.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.home.HomeRoute
import com.aliayali.marketdetail.navigation.navigateToMarketDetail
import com.aliayali.navigation.Navigator

fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator) {
    entry<HomeNavKey> {
        HomeRoute(
            onCoinClick = navigator::navigateToMarketDetail,
            onSectionMoreClick = {}
        )
    }
}