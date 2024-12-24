package com.apero.notification.executor

import android.content.Context
import com.apero.notification.NotificationContent
import com.apero.notification.ReminderType

/**
 * Created by KO Huyn on 14/08/2023.
 */
internal interface NotificationExecutor {
    fun pushAfter(context: Context, type: NotificationContent, reminderType: ReminderType.OneTime)
    fun pushInterval(context: Context, type: NotificationContent, reminderType: ReminderType.Schedule)
    fun cancel(context: Context, reminderId: Int)

    companion object {
        @JvmStatic
        fun create(): NotificationExecutor = AlarmManagerNotifyExecutor.getInstance()
    }
}