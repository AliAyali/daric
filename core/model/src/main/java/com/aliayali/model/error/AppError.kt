package com.aliayali.model.error

sealed interface AppError {

    data object NoInternet : AppError

    data object Timeout : AppError

    data class Server(
        val code: Int,
    ) : AppError

    data object Unknown : AppError
}