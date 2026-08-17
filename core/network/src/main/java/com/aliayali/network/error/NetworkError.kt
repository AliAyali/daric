package com.aliayali.network.error

sealed interface NetworkError {

    data object NoInternet : NetworkError

    data object Timeout : NetworkError

    data class Http(
        val code: Int,
    ) : NetworkError

    data object Unknown : NetworkError
}