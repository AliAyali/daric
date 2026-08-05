package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.home.model.MarketStatus
import com.aliayali.home.model.CoinUiModel
import com.aliayali.home.model.MarketOverviewCardUiModel
import com.aliayali.home.model.MarketSectionCardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {

                val overview = MarketOverviewCardUiModel(
                    marketStatus = MarketStatus.Volatile,

                    insightTitle = "بازار امروز آرام نیست معامله نکنید",

                    insightDescription = "دلار رشد ملایمی داشته و طلا تغییر محسوسی نسبت به روز گذشته است.",

                    usd = CoinUiModel(
                        id = "usd",
                        symbol = "USD",
                        name = "دلار آمریکا",
                        formattedTomanPrice = "85,000 ت",
                        formattedDollarPrice = "$1",
                        formattedChange = "+1.24%",
                        isPositive = true,
                    ),

                    gold18 = CoinUiModel(
                        id = "gold18",
                        symbol = "GOLD",
                        name = "طلای ۱۸ عیار",
                        formattedTomanPrice = "18,603,080 ت",
                        formattedDollarPrice = "$41",
                        formattedChange = "-0.31%",
                        isPositive = false,
                    ),
                )

                val sections = listOf(

                    MarketSectionCardUiModel(
                        title = "ارزهای دیجیتال",
                        items = listOf(
                            CoinUiModel(
                                id = "btc",
                                symbol = "BTC",
                                name = "Bitcoin",
                                formattedTomanPrice = "3,850,000,000 ت",
                                formattedDollarPrice = "$21,388",
                                formattedChange = "+2.45%",
                                isPositive = true,
                            ),
                            CoinUiModel(
                                id = "eur",
                                symbol = "EUR",
                                name = "eur",
                                formattedTomanPrice = "3,850 ت",
                                formattedDollarPrice = "$21",
                                formattedChange = "+8.45%",
                                isPositive = false,
                            ),
                            CoinUiModel(
                                id = "eth",
                                symbol = "ETH",
                                name = "eth",
                                formattedTomanPrice = "3,850,000,000 ت",
                                formattedDollarPrice = "$21,388",
                                formattedChange = "+2.45%",
                                isPositive = true,
                            )
                        )
                    ),

                    MarketSectionCardUiModel(
                        title = "ارزهای رایج",
                        items = listOf(
                            CoinUiModel(
                                id = "usd",
                                symbol = "USD",
                                name = "دلار آمریکا",
                                formattedTomanPrice = "85,320 تومان",
                                formattedDollarPrice = "$1",
                                formattedChange = "+1.24%",
                                isPositive = true,
                            )
                        )
                    ),

                    MarketSectionCardUiModel(
                        title = "طلا و فلزات",
                        items = listOf(
                            CoinUiModel(
                                id = "gold18",
                                symbol = "GOLD",
                                name = "طلای ۱۸ عیار",
                                formattedTomanPrice = "7,210,000 تومان",
                                formattedDollarPrice = "$41",
                                formattedChange = "-0.31%",
                                isPositive = false,
                            )
                        )
                    )

                )

                _uiState.value = HomeUiState.Success(
                    overview = overview,
                    sections = sections,
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
            HomeEvent.Refresh -> {
                loadHomeData()
            }

            is HomeEvent.CoinClick -> {}

            HomeEvent.SectionMoreClick -> {}
        }
    }
}