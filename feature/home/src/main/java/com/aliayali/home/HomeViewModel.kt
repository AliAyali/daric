package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetHomeMarketDataUseCase
import com.aliayali.home.mapper.asUiData
import com.aliayali.model.result.AppResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeMarketDataUseCase: GetHomeMarketDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(
        HomeUiState.Loading,
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            when (val result = getHomeMarketDataUseCase()) {
                is AppResult.Success -> {
                    val uiData = result.data.asUiData()

                    _uiState.value = HomeUiState.Success(
                        overview = uiData.overview,
                        coins = uiData.coins,
                        marketAssets = uiData.marketAssets,
                    )
                }

                is AppResult.Failure -> {
                    _uiState.value = HomeUiState.Error(
                        error = result.error,
                    )
                }
            }
        }
    }

    private fun refresh() {
        val currentState = _uiState.value

        if (currentState !is HomeUiState.Success) return
        if (currentState.isRefreshing) return

        viewModelScope.launch {
            _uiState.update {
                currentState.copy(
                    isRefreshing = true,
                )
            }

            when (val result = getHomeMarketDataUseCase()) {
                is AppResult.Success -> {
                    val uiData = result.data.asUiData()

                    _uiState.value = HomeUiState.Success(
                        overview = uiData.overview,
                        coins = uiData.coins,
                        marketAssets = uiData.marketAssets,
                        isRefreshing = false,
                    )
                }

                is AppResult.Failure -> {
                    _uiState.value = currentState.copy(
                        isRefreshing = false,
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> refresh()

            is HomeEvent.CoinClick -> Unit

            HomeEvent.SectionMoreClick -> Unit
        }
    }
}