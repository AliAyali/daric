package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetHomeMarketDataUseCase
import com.aliayali.home.mapper.asUiData
import com.aliayali.home.model.HomeUiData
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

    private suspend fun fetchHomeData(): HomeUiData {
        val homeData = getHomeMarketDataUseCase()

        return homeData.asUiData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val uiData = fetchHomeData()

                _uiState.value = HomeUiState.Success(
                    overview = uiData.overview,
                    coins = uiData.coins,
                    marketAssets = uiData.marketAssets,
                    isRefreshing = false,
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Unknown error",
                )
            }
        }
    }

    private fun refresh() {
        val currentState = _uiState.value

        if (currentState !is HomeUiState.Success) {
            return
        }

        if (currentState.isRefreshing) {
            return
        }

        viewModelScope.launch {
            _uiState.update {
                currentState.copy(
                    isRefreshing = true,
                )
            }

            try {
                val uiData = fetchHomeData()

                _uiState.value = HomeUiState.Success(
                    overview = uiData.overview,
                    coins = uiData.coins,
                    marketAssets = uiData.marketAssets,
                    isRefreshing = false,
                )
            } catch (e: Exception) {
                _uiState.update {
                    currentState.copy(
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