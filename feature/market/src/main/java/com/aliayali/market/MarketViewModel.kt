package com.aliayali.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MarketUiState>(
        MarketUiState.Loading,
    )

    val uiState: StateFlow<MarketUiState> =
        _uiState.asStateFlow()

    fun onEvent(event: MarketEvent) {
        when (event) {
            is MarketEvent.SelectTab -> {
                _uiState.update { state ->
                    if (state is MarketUiState.Success) {
                        state.copy(
                            selectedTab = event.tab,
                        )
                    } else {
                        state
                    }
                }
            }

            MarketEvent.Refresh -> {}
            MarketEvent.Retry -> {}
        }
    }
}