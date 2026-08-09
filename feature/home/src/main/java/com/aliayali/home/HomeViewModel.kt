package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetMarketCoinsUseCase
import com.aliayali.home.mapper.asUiModel
import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarketCoinsUseCase: GetMarketCoinsUseCase,
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
                val coins = getMarketCoinsUseCase()
                    .map { it.asUiModel() }

                _uiState.value = HomeUiState.Success(
                    overview = createMarketOverview(coins),
                    coins = coins,
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

    private fun createMarketOverview(
        coins: List<CoinUiModel>,
    ): MarketOverviewCardUiModel {
        return MarketOverviewCardUiModel(
            marketStatus = MarketStatus.Volatile,
            insightTitle = "بازار در وضعیت نوسانی",
            insightDescription = "تحلیل بازار به‌زودی اضافه می‌شود.",
            usd = coins.first(),
            gold18 = coins.getOrNull(1) ?: coins.first(),
        )
    }
}