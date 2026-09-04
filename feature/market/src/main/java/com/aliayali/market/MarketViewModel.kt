package com.aliayali.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetMarketPageDataUseCase
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.market.mapper.asUiModel
import com.aliayali.market.model.MarketListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getMarketPageDataUseCase: GetMarketPageDataUseCase,
    private val marketSyncer: MarketSyncer,
    private val observeNetworkConnectivityUseCase: ObserveNetworkConnectivityUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MarketUiState>(
        MarketUiState.Success(),
    )

    val uiState: StateFlow<MarketUiState> =
        _uiState.asStateFlow()

    private var isOnline: Boolean? = null

    init {
        observeNetwork()
        observeMarketData()
        performSync()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkConnectivityUseCase()
                .collect { online ->

                    val wasOffline = isOnline == false
                    isOnline = online

                    _uiState.update { currentState ->
                        if (currentState is MarketUiState.Success) {
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

    private fun observeMarketData() {
        viewModelScope.launch {
            getMarketPageDataUseCase()
                .collect { marketPageData ->
                    val dollarToToman = marketPageData.marketAssets
                        .firstOrNull { it.symbol == "USD" }
                        ?.price
                    _uiState.update { state ->
                        if (state is MarketUiState.Success) {
                            state.copy(
                                cryptoState = MarketListState.Success(
                                    items = marketPageData.coins.map { coin ->
                                        coin.asUiModel(dollarToToman)
                                    },
                                ),
                                marketAssetState = MarketListState.Success(
                                    items = marketPageData.marketAssets.map {
                                        it.asUiModel()
                                    },
                                ),
                            )
                        } else {
                            state
                        }
                    }
                }
        }
    }

    private fun performSync(
        showRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {

            if (showRefreshing) {
                _uiState.update { currentState ->
                    if (currentState is MarketUiState.Success) {
                        currentState.copy(
                            isRefreshing = true,
                        )
                    } else {
                        currentState
                    }
                }
            }

            when (val result = marketSyncer.sync()) {

                is AppResult.Success -> {
                    _uiState.update { currentState ->
                        if (currentState is MarketUiState.Success) {
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
                            is MarketUiState.Success -> {
                                currentState.copy(
                                    isRefreshing = false,
                                    isOffline = true,
                                )
                            }

                            else -> {
                                MarketUiState.Error(
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

        if (currentState !is MarketUiState.Success) {
            return
        }

        if (currentState.isRefreshing) {
            return
        }

        performSync(showRefreshing = true)
    }

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

            MarketEvent.Refresh -> refresh()
        }
    }
}