package com.aliayali.network.error

class NetworkException(
    val error: NetworkError,
    cause: Throwable? = null,
) : Exception(cause)