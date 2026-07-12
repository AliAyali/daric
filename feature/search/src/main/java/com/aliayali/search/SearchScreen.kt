package com.aliayali.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = SearchViewModel(),
) {
    Text(
        text = "Search"
    )
}