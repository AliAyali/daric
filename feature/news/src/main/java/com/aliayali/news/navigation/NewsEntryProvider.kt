package com.aliayali.news.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.aliayali.navigation.Navigator
import com.aliayali.news.NewsRoute

fun EntryProviderScope<NavKey>.newsEntry(navigator: Navigator) {
    entry<NewsNavKey> {
        NewsRoute()
    }
}