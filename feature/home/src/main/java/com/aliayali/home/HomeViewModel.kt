package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetHomeMarketDataUseCase
import com.aliayali.home.mapper.asUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

            try {
                val homeData = getHomeMarketDataUseCase()

                val uiData = homeData.asUiData()

                _uiState.value = HomeUiState.Success(
                    overview = uiData.overview,
                    coins = uiData.coins,
                    marketAssets = uiData.marketAssets,
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Unknown error",
                )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> loadHomeData()

            is HomeEvent.CoinClick -> Unit

            HomeEvent.SectionMoreClick -> Unit
        }
    }
}