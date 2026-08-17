package com.aliayali.model.result

import com.aliayali.model.error.AppError

sealed interface AppResult<out T> {

    data class Success<T>(
        val data: T,
    ) : AppResult<T>

    data class Failure(
        val error: AppError,
    ) : AppResult<Nothing>
}