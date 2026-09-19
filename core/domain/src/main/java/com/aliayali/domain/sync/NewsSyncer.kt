package com.aliayali.domain.sync

import com.aliayali.common.result.AppResult

interface NewsSyncer {

    suspend fun sync(
        category: String,
        query: String,
    ): AppResult<Unit>
}