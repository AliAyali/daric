package com.aliayali.notifications

interface Notifier {
    fun postNotification(
        title: String,
        message: String,
    )
}