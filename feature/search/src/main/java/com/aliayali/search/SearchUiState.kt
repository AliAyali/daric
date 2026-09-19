package com.aliayali.search

import com.aliayali.common.error.AppError
import com.aliayali.search.model.SearchItemUiModel

sealed interface SearchUiState {

    val query: String

    data class Idle(
        override val query: String = "",
    ) : SearchUiState

    data class Loading(
        override val query: String,
    ) : SearchUiState

    data class Success(
        override val query: String,
        val results: List<SearchItemUiModel>,
    ) : SearchUiState

    data class Empty(
        override val query: String,
    ) : SearchUiState

    data class Error(
        override val query: String,
        val error: AppError,
    ) : SearchUiState
}