package com.aliayali.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.aliayali.search.components.SearchEmpty
import com.aliayali.search.components.SearchInitial
import com.aliayali.search.components.SearchItem
import com.aliayali.search.components.error.SearchError
import com.aliayali.search.components.loading.SearchLoading
import com.aliayali.search.components.search.SearchToolbar
import com.aliayali.search.model.SearchItemUiModel

@Composable
internal fun SearchScreen(
    uiState: SearchUiState,
    onEvent: (SearchEvent) -> Unit,
    onBackClick: () -> Unit,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchFieldValue by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
    ) {
        mutableStateOf(
            TextFieldValue(
                text = uiState.query,
            )
        )
    }

    LaunchedEffect(uiState.query) {
        if (searchFieldValue.text != uiState.query) {
            searchFieldValue = searchFieldValue.copy(
                text = uiState.query,
                selection = TextRange(uiState.query.length),
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {

        SearchToolbar(
            searchFieldValue = searchFieldValue,
            onSearchFieldValueChanged = { value ->
                searchFieldValue = value

                onEvent(
                    SearchEvent.QueryChanged(
                        query = value.text,
                    )
                )
            },
            onBackClick = onBackClick,
        )

        SearchContent(
            uiState = uiState,
            onCoinClick = onCoinClick,
            onMarketAssetClick = onMarketAssetClick,
        )
    }
}

@Composable
private fun SearchContent(
    uiState: SearchUiState,
    onCoinClick: (String) -> Unit,
    onMarketAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {

        is SearchUiState.Idle -> {
            SearchInitial(
                modifier = modifier,
            )
        }

        is SearchUiState.Loading -> {
            repeat(7) {
                SearchLoading(
                    modifier = modifier,
                )
            }
        }

        is SearchUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = uiState.results,
                    key = { item ->
                        when (item) {
                            is SearchItemUiModel.Coin ->
                                "coin_${item.id}"

                            is SearchItemUiModel.MarketAsset ->
                                "market_${item.id}"
                        }
                    },
                ) { item ->
                    SearchItem(
                        item = item,
                        onCoinClick = onCoinClick,
                        onMarketAssetClick = onMarketAssetClick,
                    )
                }
            }
        }

        is SearchUiState.Empty -> {
            SearchEmpty(
                modifier = modifier,
            )
        }

        is SearchUiState.Error -> {
            SearchError(
                modifier = modifier,
            )
        }
    }
}