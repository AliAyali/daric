package com.aliayali.data.error

import com.aliayali.common.error.AppError
import com.aliayali.network.error.NetworkError
import com.aliayali.network.error.NetworkException

fun Throwable.asAppError(): AppError {
    val networkError = when (this) {
        is NetworkException -> error
        else -> return AppError.Unknown
    }

    return when (networkError) {
        NetworkError.NoInternet ->
            AppError.NoInternet

        NetworkError.Timeout ->
            AppError.Timeout

        is NetworkError.Http ->
            AppError.Server(
                code = networkError.code,
            )

        NetworkError.Unknown ->
            AppError.Unknown
    }
}