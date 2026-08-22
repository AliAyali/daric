package com.aliayali.domain.sync

import com.aliayali.common.result.AppResult

interface MarketSyncer {
    suspend fun sync(): AppResult<Unit>
}