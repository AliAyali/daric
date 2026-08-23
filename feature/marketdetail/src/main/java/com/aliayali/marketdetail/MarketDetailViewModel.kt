package com.aliayali.marketdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MarketDetailViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<MarketDetailUiState>(
        MarketDetailUiState.Loading,
    )

    val uiState: StateFlow<MarketDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: MarketDetailEvent) {
        when (event) {
            MarketDetailEvent.Refresh -> Unit
        }
    }
}