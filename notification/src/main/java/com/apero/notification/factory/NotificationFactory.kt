package com.apero.notification.factory

import android.app.Notification
import android.content.Context
import com.apero.notification.NotificationContent

/**
 * Created by KO Huyn on 14/08/2023.
 */
internal interface NotificationFactory {
    fun pushNotification(notificationContent: NotificationContent)
    fun createNotification(notificationContent: NotificationContent): Notification?
    fun cancel(id: Int)
    fun isActive(id: Int): Boolean

    companion object {
        @JvmStatic
        fun getInstance(context: Context): NotificationFactory =
            NotificationFactoryImpl(context)
    }
}