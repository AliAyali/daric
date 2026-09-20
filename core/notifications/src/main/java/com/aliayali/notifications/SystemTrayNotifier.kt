package com.aliayali.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val NOTIFICATION_CHANNEL_ID = "daric_notifications"
private const val NOTIFICATION_CHANNEL_NAME = "Daric Notifications"
private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications from Daric"
private const val NOTIFICATION_ID = 1001

@Singleton
internal class SystemTrayNotifier @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : Notifier {

    override fun postNotification(
        title: String,
        message: String,
    ) {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createNotificationChannel()

        val titleText = SpannableString(title).apply {
            setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }

        val messageText = SpannableString(message).apply {
            setSpan(
                AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),
                0,
                length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }

        val notification = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID,
        )
            .setSmallIcon(R.drawable.core_notifications_ic_notification)
            .setContentTitle(titleText)
            .setContentText(messageText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                NOTIFICATION_ID,
                notification,
            )
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = NOTIFICATION_CHANNEL_DESCRIPTION
        }

        NotificationManagerCompat
            .from(context)
            .createNotificationChannel(channel)
    }
}
