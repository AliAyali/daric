package com.aliayali.common.error

sealed interface AppError {

    data object NoInternet : AppError

    data object Timeout : AppError

    data class Server(
        val code: Int,
    ) : AppError

    data object Unknown : AppError
}

val AppError.isRetryable: Boolean
    get() = when (this) {
        AppError.NoInternet -> true
        AppError.Timeout -> true

        is AppError.Server -> code in 500..599

        AppError.Unknown -> false
    }