package com.aliayali.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.GetMarketOverviewUseCase
import com.aliayali.domain.GetMarketSectionsUseCase
import com.aliayali.home.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMarketOverviewUseCase: GetMarketOverviewUseCase,
    private val getMarketSectionsUseCase: GetMarketSectionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {

                val overview = getMarketOverviewUseCase()
                    .asUiModel()

                val sections = getMarketSectionsUseCase()
                    .map { it.asUiModel() }

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