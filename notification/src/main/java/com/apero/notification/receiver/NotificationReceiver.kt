package com.apero.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apero.notification.Logger
import com.apero.notification.NotificationContent
import com.apero.notification.ReminderType
import com.apero.notification.executor.NotificationExecutor
import com.apero.notification.factory.NotificationFactory

/**
 * Created by KO Huyn on 11/08/2023.
 */
internal class NotificationReceiver : BroadcastReceiver() {
    companion object {
        const val ARG_TYPE_NOTIFICATION = "ARG_TYPE_NOTIFICATION"
        const val ARG_TYPE_REMINDER = "ARG_TYPE_REMINDER"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            kotlin.runCatching {
                val notificationContent = kotlin.runCatching {
                    intent.getParcelableExtra<NotificationContent>(ARG_TYPE_NOTIFICATION)
                }.getOrNull()
                val reminderType = kotlin.runCatching {
                    intent.getParcelableExtra<ReminderType>(ARG_TYPE_REMINDER)
                }.getOrNull()
                notificationContent?.let {
                    NotificationFactory.getInstance(context).pushNotification(notificationContent)
                }
                if (notificationContent != null) {
                    if (reminderType is ReminderType.Schedule) {
                        NotificationExecutor.create()
                            .pushInterval(context, notificationContent, reminderType)
                    }
                }
            }
        }
    }
}