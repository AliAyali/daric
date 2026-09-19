package com.aliayali.market.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aliayali.market.R
import com.aliayali.market.model.MarketTab

@Composable
fun MarketTabs(
    selectedTab: MarketTab,
    onTabSelected: (MarketTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = MarketTab.entries
    val selectedTabIndex = tabs.indexOf(selectedTab)

    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.fillMaxWidth(),
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedTabIndex = selectedTabIndex,
                    matchContentSize = true,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = {
                    onTabSelected(tab)
                },
                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = {
                    Text(
                        text = when (tab) {
                            MarketTab.CRYPTO ->
                                stringResource(
                                    R.string.feature_market_tab_crypto
                                )

                            MarketTab.MARKET_ASSET ->
                                stringResource(
                                    R.string.feature_market_tab_market_asset
                                )
                        },
                    )
                },
            )
        }
    }
}