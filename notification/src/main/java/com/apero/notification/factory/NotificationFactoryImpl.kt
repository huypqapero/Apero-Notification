package com.apero.notification.factory

import android.app.Notification
import android.content.Context
import com.apero.notification.Logger
import com.apero.notification.NotificationContent
import com.apero.notification.getNotificationManager


/**
 * Created by KO Huyn on 14/08/2023.
 */
internal class NotificationFactoryImpl(private val context: Context) : NotificationFactory {

    override fun pushNotification(notificationContent: NotificationContent) {
        Logger.i("pushed notification $notificationContent")
        val notification = createNotification(notificationContent)
        kotlin.runCatching {
            context.getNotificationManager().notify(notificationContent.getNotifyId(), notification)
        }.onFailure {
            throw IllegalArgumentException("Create ${notificationContent::class.java.simpleName}.getBy(context: Context) exception with cause:${it.message}", it)
        }
    }

    override fun createNotification(notificationContent: NotificationContent): Notification? {
        return notificationContent.getBy(context)
    }

    override fun cancel(id: Int) {
        context.getNotificationManager().cancel(id)
    }

    override fun isActive(id: Int): Boolean {
        return context.getNotificationManager().activeNotifications.map { it.id }.contains(id)
    }
}