package com.aliayali.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetNewsUseCase
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.sync.NewsSyncer
import com.aliayali.news.mapper.asUiModel
import com.aliayali.news.model.NewsCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val observeNetworkConnectivityUseCase: ObserveNetworkConnectivityUseCase,
    private val newsSyncer: NewsSyncer,
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(
        NewsUiState.Loading,
    )

    val uiState: StateFlow<NewsUiState> =
        _uiState.asStateFlow()

    private val selectedCategory =
        MutableStateFlow(NewsCategory.ALL)

    private var hasLocalData = false

    private var isOnline: Boolean? = null

    init {
        observeNetwork()
        observeNews()
        performSync()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkConnectivityUseCase()
                .collect { online ->

                    val wasOffline = isOnline == false

                    isOnline = online

                    _uiState.update { currentState ->
                        if (currentState is NewsUiState.Success) {
                            currentState.copy(
                                isOffline = !online,
                            )
                        } else {
                            currentState
                        }
                    }

                    if (wasOffline && online) {
                        refresh()
                    }
                }
        }
    }

    private fun observeNews() {
        viewModelScope.launch {
            selectedCategory
                .flatMapLatest { category ->
                    getNewsUseCase(
                        category = category.key,
                    )
                }
                .collectLatest { news ->

                    if (news.isEmpty()) {
                        return@collectLatest
                    }

                    hasLocalData = true

                    val uiNews = news.map { it.asUiModel() }

                    _uiState.update { currentState ->
                        when (currentState) {

                            is NewsUiState.Success -> {
                                currentState.copy(
                                    news = uiNews,
                                )
                            }

                            else -> {
                                NewsUiState.Success(
                                    news = uiNews,
                                    isRefreshing = false,
                                    isOffline = isOnline != true,
                                    selectedCategory =
                                        selectedCategory.value,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun performSync(
        showRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {

            if (!hasLocalData && !showRefreshing) {
                _uiState.value = NewsUiState.Loading
            }

            if (showRefreshing) {
                _uiState.update { currentState ->
                    if (currentState is NewsUiState.Success) {
                        currentState.copy(
                            isRefreshing = true,
                        )
                    } else {
                        currentState
                    }
                }
            }

            val category = selectedCategory.value

            when (
                val result = newsSyncer.sync(
                    category = category.key,
                    query = category.query,
                )
            ) {

                is AppResult.Success -> {
                    _uiState.update { currentState ->
                        if (currentState is NewsUiState.Success) {
                            currentState.copy(
                                isRefreshing = false,
                                isOffline = false,
                            )
                        } else {
                            currentState
                        }
                    }
                }

                is AppResult.Failure -> {
                    _uiState.update { currentState ->

                        when (currentState) {

                            is NewsUiState.Success -> {
                                currentState.copy(
                                    isRefreshing = false,
                                    isOffline = true,
                                )
                            }

                            else -> {
                                NewsUiState.Error(
                                    error = result.error,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun refresh() {

        val currentState = _uiState.value

        if (currentState !is NewsUiState.Success) {
            return
        }

        if (currentState.isRefreshing) {
            return
        }

        performSync(
            showRefreshing = true,
        )
    }

    private fun onCategorySelected(
        category: NewsCategory,
    ) {
        if (selectedCategory.value == category) {
            return
        }

        selectedCategory.value = category

        hasLocalData = false

        _uiState.update { currentState ->
            when (currentState) {

                is NewsUiState.Success -> {
                    currentState.copy(
                        selectedCategory = category,
                        isRefreshing = true,
                    )
                }

                else -> currentState
            }
        }

        performSync(
            showRefreshing = true,
        )
    }

    fun onEvent(event: NewsEvent) {
        when (event) {

            NewsEvent.Refresh -> {
                refresh()
            }

            is NewsEvent.SelectedCategory -> {
                onCategorySelected(
                    category = event.category,
                )
            }
        }
    }
}