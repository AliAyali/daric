package com.aliayali.daric.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.aliayali.daric.R
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.home.navigation.HomeNavKey
import com.aliayali.market.navigation.MarketNavKey
import com.aliayali.home.R as homeR
import com.aliayali.market.R as marketR

data class TopLevelNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @param:StringRes val iconTextId: Int,
    @param:StringRes val titleTextId: Int,
)

val HOME = TopLevelNavItem(
    selectedIcon = DaricIcons.Home,
    unselectedIcon = DaricIcons.HomeBorder,
    iconTextId = homeR.string.feature_home_title,
    titleTextId = R.string.app_name,
)

val MARKET = TopLevelNavItem(
    selectedIcon = DaricIcons.Market,
    unselectedIcon = DaricIcons.MarketBorder,
    iconTextId = marketR.string.feature_market_title,
    titleTextId = R.string.app_name,
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    MarketNavKey to MARKET,
)