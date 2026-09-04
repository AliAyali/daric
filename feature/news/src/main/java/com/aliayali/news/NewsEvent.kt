package com.aliayali.news

sealed interface NewsEvent {
    data object Refresh : NewsEvent
}