package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetMarketAssetsUseCase
import com.aliayali.domain.GetMarketCoinsUseCase
import com.aliayali.home.mapper.asUiModel
import com.aliayali.home.model.MarketAssetUiModel
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
    private val getMarketAssetsUseCase: GetMarketAssetsUseCase,
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
                val marketData = getMarketCoinsUseCase()

                val marketAssets = getMarketAssetsUseCase()
                    .map { it.asUiModel() }

                val dollarToToman = marketAssets
                    .firstOrNull { it.symbol == "USD" }
                    ?.price

                val coins = marketData.coins.map {
                    it.asUiModel(dollarToToman)
                }

                _uiState.value = HomeUiState.Success(
                    overview = createMarketOverview(marketAssets),
                    coins = coins,
                    marketAssets = marketAssets,
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
        marketAssets: List<MarketAssetUiModel>,
    ): MarketOverviewCardUiModel {
        val usd = marketAssets.firstOrNull {
            it.symbol == "USD"
        }
        val gold18 = marketAssets.firstOrNull {
            it.symbol == "IR_GOLD_18K"
        }
        return MarketOverviewCardUiModel(
            marketStatus = MarketStatus.Volatile,
            insightTitle = "بازار در وضعیت نوسانی",
            insightDescription = "تحلیل بازار به‌زودی اضافه می‌شود.",
            usd = usd ?: error("USD not found"),
            gold18 = gold18 ?: error("18K Gold not found"),
        )
    }
}