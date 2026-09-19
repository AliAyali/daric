package com.aliayali.daric.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.aliayali.daric.navigation.TOP_LEVEL_NAV_ITEMS
import com.aliayali.home.navigation.HomeNavKey
import com.aliayali.navigation.NavigationState
import com.aliayali.navigation.rememberNavigationState

class DaricAppState(
    val navigationState: NavigationState,
)

@Composable
fun rememberDaricAppState(): DaricAppState {

    val navigationState = rememberNavigationState(
        startKey = HomeNavKey,
        topLevelKeys = TOP_LEVEL_NAV_ITEMS.keys,
    )

    return remember {
        DaricAppState(
            navigationState = navigationState,
        )
    }
}