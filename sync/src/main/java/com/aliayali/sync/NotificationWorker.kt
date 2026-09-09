package com.aliayali.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aliayali.domain.settings.ObserveNotificationsUseCase
import com.aliayali.notifications.Notifier
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notifier: Notifier,
    private val observeNotificationsEnabledUseCase: ObserveNotificationsUseCase,
) : CoroutineWorker(
    appContext,
    workerParams,
) {

    override suspend fun doWork(): Result {
        if (observeNotificationsEnabledUseCase().first()) {
            notifier.postNotification(
                title = applicationContext.getString(R.string.sync_notification_title),
                message = applicationContext.getString(R.string.sync_notification_message),
            )
        }

        return Result.success()
    }
}