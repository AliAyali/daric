package com.aliayali.domain.sync

import com.aliayali.model.result.AppResult

interface MarketSyncer {
    suspend fun sync(): AppResult<Unit>
}