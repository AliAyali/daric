package com.aliayali.marketdetail.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.marketdetail.MarketDetailRoute
import com.aliayali.marketdetail.MarketDetailViewModel
import com.aliayali.marketdetail.MarketDetailViewModel.Factory
import com.aliayali.navigation.Navigator


fun EntryProviderScope<NavKey>.marketDetailEntry(
    navigator: Navigator,
) {
    entry<MarketDetailNavKey> { key ->

        val viewModel = hiltViewModel<MarketDetailViewModel, Factory>(
            creationCallback = { factory ->
                factory.create(key)
            },
        )

        MarketDetailRoute(
            viewModel = viewModel,
            onBackClick = { navigator.goBack() }
        )
    }
}

fun Navigator.navigateToMarketDetail(
    assetId: String,
    assetType: MarketDetailAssetType,
) {
    navigate(
        MarketDetailNavKey(
            assetId = assetId,
            assetType = assetType,
        ),
    )
}