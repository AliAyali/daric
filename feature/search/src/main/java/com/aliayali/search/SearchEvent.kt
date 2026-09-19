package com.aliayali.search

import com.aliayali.search.model.SearchItemUiModel

sealed interface SearchEvent {

    data class QueryChanged(
        val query: String,
    ) : SearchEvent

    data object ClearQuery : SearchEvent

    data class ItemClicked(
        val item: SearchItemUiModel,
    ) : SearchEvent
}