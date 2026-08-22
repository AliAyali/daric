package com.aliayali.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aliayali.common.error.isRetryable
import com.aliayali.common.result.AppResult
import com.aliayali.domain.sync.MarketSyncer
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MarketSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val marketSyncer: MarketSyncer,
) : CoroutineWorker(
    appContext,
    workerParams,
) {

    override suspend fun doWork(): Result {
        return when (val result = marketSyncer.sync()) {
            is AppResult.Success -> {
                Result.success()
            }

            is AppResult.Failure -> {
                if (result.error.isRetryable) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }
        }
    }
}