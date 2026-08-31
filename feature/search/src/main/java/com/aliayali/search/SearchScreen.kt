package com.aliayali.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.search.components.SearchEmpty
import com.aliayali.search.components.SearchItem
import com.aliayali.search.components.error.SearchError
import com.aliayali.search.components.loading.SearchLoading
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
    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        SearchToolbar(
            searchQuery = uiState.query,
            onSearchQueryChanged = {
                onEvent(
                    SearchEvent.QueryChanged(it)
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

        is SearchUiState.Idle -> {}

        is SearchUiState.Loading -> {
            SearchLoading(
                modifier = modifier,
            )
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
                query = uiState.query,
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

@Composable
private fun SearchToolbar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
        )
        IconButton(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .1f),
                shape = CircleShape
            ),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = DaricIcons.ArrowDown,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = DaricIcons.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = DaricIcons.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) {
                onSearchQueryChanged(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    keyboardController?.hide()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(32.dp),
        value = searchQuery,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}