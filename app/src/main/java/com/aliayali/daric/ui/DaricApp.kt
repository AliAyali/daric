package com.aliayali.daric.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.aliayali.daric.navigation.TOP_LEVEL_NAV_ITEMS
import com.aliayali.designsystem.component.DaricNavigationSuiteScaffold
import com.aliayali.designsystem.component.DaricTopAppBar
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.home.navigation.homeEntry
import com.aliayali.marketdetail.navigation.MarketDetailNavKey
import com.aliayali.marketdetail.navigation.marketDetailEntry
import com.aliayali.navigation.Navigator
import com.aliayali.navigation.toEntries
import com.aliayali.search.navigation.SearchNavKey
import com.aliayali.search.navigation.searchEntry
import com.aliayali.setting.SettingsDialog
import com.aliayali.setting.R as settingR

@Composable
fun DaricApp(
    appState: DaricAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    DaricApp(
        appState = appState,
        modifier = modifier,
        showSettingsDialog = showSettingsDialog,
        onSettingsDismissed = { showSettingsDialog = false },
        onTopAppBarActionClick = { showSettingsDialog = true },
        windowAdaptiveInfo = windowAdaptiveInfo,
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun DaricApp(
    appState: DaricAppState,
    modifier: Modifier,
    showSettingsDialog: Boolean,
    onSettingsDismissed: () -> Unit,
    onTopAppBarActionClick: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val navigator = remember(appState.navigationState) {
        Navigator(appState.navigationState)
    }
    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { onSettingsDismissed() },
        )
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    DaricNavigationSuiteScaffold(
        windowAdaptiveInfo = windowAdaptiveInfo,
        navigationSuiteItems = {
            TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                val selected = navKey == appState.navigationState.currentTopLevelKey
                item(
                    selected = selected,
                    onClick = { navigator.navigate(navKey) },
                    icon = {
                        Icon(
                            imageVector = if (selected) {
                                navItem.selectedIcon
                            } else {
                                navItem.unselectedIcon
                            },
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(stringResource(navItem.iconTextId))
                    }
                )
            }
        },
    ) {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                var shouldShowTopAppBar = false
                if (appState.navigationState.currentKey in appState.navigationState.topLevelKeys) {
                    shouldShowTopAppBar = true

                    val destination =
                        TOP_LEVEL_NAV_ITEMS[appState.navigationState.currentTopLevelKey]
                            ?: error("Top level nav item not found for ${appState.navigationState.currentTopLevelKey}")

                    DaricTopAppBar(
                        titleRes = destination.titleTextId,
                        navigationIcon = DaricIcons.Search,
                        navigationIconContentDescription = stringResource(
                            id = settingR.string.top_app_bar_search,
                        ),
                        actionIcon = DaricIcons.Settings,
                        actionIconContentDescription = stringResource(
                            id = settingR.string.top_app_bar_settings
                        ),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                        onActionClick = { onTopAppBarActionClick() },
                        onNavigationClick = {
                            navigator.navigate(SearchNavKey)
                        },
                    )
                }
                Box(
                    modifier = Modifier.consumeWindowInsets(
                        if (shouldShowTopAppBar) {
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        } else {
                            WindowInsets(0, 0, 0, 0)
                        },
                    ),
                ) {
                    val entryProvider = entryProvider {
                        homeEntry(navigator)
                        marketDetailEntry(navigator)
                        searchEntry(navigator)
                    }
                    NavDisplay(
                        entries = appState.navigationState.toEntries(entryProvider),
                        onBack = { navigator.goBack() },
                        sceneStrategy = listDetailStrategy
                    )
                }
            }
        }
    }
}