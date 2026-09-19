package com.aliayali.home

sealed interface HomeEvent {
    data object Refresh : HomeEvent
}