package com.aliayali.news

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(
        NewsUiState.Loading,
    )

    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun onEvent(event: NewsEvent) {
        when (event) {
            NewsEvent.Refresh -> Unit
        }
    }
}