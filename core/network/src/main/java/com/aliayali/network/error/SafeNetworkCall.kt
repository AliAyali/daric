package com.aliayali.network.error

suspend fun <T> safeNetworkCall(
    block: suspend () -> T,
): T {
    return try {
        block()
    } catch (throwable: Throwable) {
        throw NetworkException(
            error = throwable.asNetworkError(),
            cause = throwable,
        )
    }
}