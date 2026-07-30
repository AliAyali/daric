package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.home.model.CoinUiModel
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
                val featuredItems = listOf(
                    CoinUiModel(
                        id = "usd",
                        name = "دلار",
                        formattedPrice = "85,000",
                        imageUrl = "",
                        isPositive = true
                    )
                )

                val popularItems = listOf(
                    CoinUiModel(
                        id = "gold",
                        name = "طلای 18 عیار",
                        formattedPrice = "7,200,000",
                        imageUrl = "",
                        isPositive = false
                    )
                )

                _uiState.value = HomeUiState.Success(
                    featuredItems = featuredItems,
                    popularItems = popularItems
                )

            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }


    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.Refresh -> {
                loadHomeData()
            }
            is HomeEvent.CoinClick -> {}
        }
    }
}