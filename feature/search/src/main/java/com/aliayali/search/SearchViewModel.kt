package com.aliayali.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.error.AppError
import com.aliayali.domain.SearchMarketAssetsUseCase
import com.aliayali.search.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMarketAssetsUseCase: SearchMarketAssetsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(
        SearchUiState.Idle(),
    )

    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

    private val query = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {

            query
                .debounce(300.milliseconds)
                .distinctUntilChanged()
                .flatMapLatest { query ->

                    if (query.isBlank()) {
                        flowOf(
                            SearchUiState.Idle(
                                query = query,
                            )
                        )
                    } else {

                        searchMarketAssetsUseCase(query)
                            .map { results ->
                                if (results.isEmpty()) {
                                    SearchUiState.Empty(
                                        query = query,
                                    )
                                } else {
                                    SearchUiState.Success(
                                        query = query,
                                        results = results.map {
                                            it.asUiModel()
                                        },
                                    )
                                }
                            }
                            .onStart {
                                emit(
                                    SearchUiState.Loading(
                                        query = query,
                                    )
                                )
                            }
                            .catch { _ ->
                                emit(
                                    SearchUiState.Error(
                                        query = query,
                                        error = AppError.Unknown,
                                    )
                                )
                            }
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {

            is SearchEvent.QueryChanged -> {
                query.value = event.query
            }

            SearchEvent.ClearQuery -> {
                query.value = ""
            }

            is SearchEvent.ItemClicked -> {
                // بعداً Navigation event را مشخص می‌کنیم.
            }
        }
    }
}