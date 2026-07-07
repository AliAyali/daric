package com.aliayali.daric.ui

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.aliayali.home.navigation.homeEntry
import com.aliayali.navigation.Navigator
import com.aliayali.navigation.toEntries

@Composable
fun DaricApp(
    appState: DaricAppState,
) {

    val navigator = androidx.compose.runtime.remember {
        Navigator(appState.navigationState)
    }


    val entryProvider = entryProvider {

        homeEntry(navigator)

    }


    NavDisplay(
        entries = appState.navigationState.toEntries(entryProvider),
        onBack = {
            navigator.goBack()
        },
    )
}