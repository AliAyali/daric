package com.aliayali.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object Sync {

    private const val SYNC_WORK_NAME = "market_sync"
    private const val NOTIFICATION_WORK_NAME = "daily_notification"

    fun initialize(
        context: Context,
    ) {
        val syncRequest = OneTimeWorkRequestBuilder<MarketSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                30,
                TimeUnit.SECONDS,
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            syncRequest,
        )
    }

    fun initializeDailyNotification(
        context: Context,
    ) {
        val notificationRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(
                1,
                TimeUnit.DAYS,
            ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationRequest,
        )
    }
}