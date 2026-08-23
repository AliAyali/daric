package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.common.result.AppResult
import com.aliayali.domain.GetHomeMarketDataUseCase
import com.aliayali.domain.ObserveNetworkConnectivityUseCase
import com.aliayali.domain.sync.MarketSyncer
import com.aliayali.home.mapper.asUiData
import com.aliayali.home.model.HomeUiData
import com.aliayali.model.HomeMarketData
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
    private val observeNetworkConnectivityUseCase: ObserveNetworkConnectivityUseCase,
    private val marketSyncer: MarketSyncer,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(
        HomeUiState.Loading,
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var hasLocalData = false
    private var isOnline: Boolean? = null

    init {
        observeNetwork()
        observeHomeData()
        performSync()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            observeNetworkConnectivityUseCase()
                .collect { online ->

                    val wasOffline = isOnline == false
                    isOnline = online

                    _uiState.update { currentState ->
                        if (currentState is HomeUiState.Success) {
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

    private fun observeHomeData() {
        viewModelScope.launch {
            getHomeMarketDataUseCase
                .observeHomeMarketData()
                .collect { homeData ->

                    if (!hasUsableData(homeData)) {
                        return@collect
                    }

                    hasLocalData = true

                    val uiData = homeData.asUiData()

                    _uiState.update { homeUiState ->
                        when (homeUiState) {
                            is HomeUiState.Success -> {
                                homeUiState.copy(
                                    homeUiData = HomeUiData(
                                        overview = uiData.overview,
                                        coins = uiData.coins,
                                        marketAssets = uiData.marketAssets,
                                    )
                                )
                            }

                            else -> {
                                HomeUiState.Success(
                                    homeUiData = HomeUiData(
                                        overview = uiData.overview,
                                        coins = uiData.coins,
                                        marketAssets = uiData.marketAssets,
                                    ),
                                    isRefreshing = false,
                                    isOffline = isOnline != true,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun performSync(
        showRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {

            if (!hasLocalData && !showRefreshing) {
                _uiState.value = HomeUiState.Loading
            }

            if (showRefreshing) {
                _uiState.update { currentState ->
                    if (currentState is HomeUiState.Success) {
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
                        if (currentState is HomeUiState.Success) {
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
                            is HomeUiState.Success -> {
                                currentState.copy(
                                    isRefreshing = false,
                                    isOffline = true,
                                )
                            }

                            else -> {
                                HomeUiState.Error(
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

        if (currentState !is HomeUiState.Success) {
            return
        }

        if (currentState.isRefreshing) {
            return
        }

        performSync(showRefreshing = true)
    }

    private fun hasUsableData(
        data: HomeMarketData,
    ): Boolean {
        return data.coins.isNotEmpty() &&
                data.marketAssets.isNotEmpty()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> refresh()

            is HomeEvent.CoinClick -> Unit

            HomeEvent.SectionMoreClick -> Unit
        }
    }
}