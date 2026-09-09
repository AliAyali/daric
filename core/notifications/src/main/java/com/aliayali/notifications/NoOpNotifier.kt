package com.aliayali.notifications

import javax.inject.Inject

internal class NoOpNotifier @Inject constructor() : Notifier {

    override fun postNotification(
        title: String,
        message: String,
    ) = Unit
}