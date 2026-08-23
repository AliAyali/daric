package com.aliayali.network.error

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.asNetworkError(): NetworkError {
    return when (this) {
        is SocketTimeoutException ->
            NetworkError.Timeout

        is HttpException ->
            NetworkError.Http(
                code = code(),
            )

        is IOException ->
            NetworkError.NoInternet

        else ->
            NetworkError.Unknown
    }
}