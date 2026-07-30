package com.aliayali.home

sealed interface HomeEvent {
    data object Refresh : HomeEvent
    data class CoinClick(val id: String) : HomeEvent
}