package com.apero.notification

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import com.apero.notification.executor.NotificationExecutor
import com.apero.notification.factory.NotificationFactory

/**
 * Created by KO Huyn on 23/12/2024.
 */
object NotificationSDK {
    private var _application: Application? = null

    private val notificationFactory by lazy { NotificationFactory.getInstance(getApplication()) }

    internal fun getApplication(): Application {
        return _application
            ?: throw IllegalArgumentException("You need install SDK with NotificationSDK.install(application) in your application")
    }

    fun install(application: Application) {
        this._application = application
    }

    @Throws(IllegalArgumentException::class)
    fun pushNotificationReminder(
        notificationContent: NotificationContent,
        reminderType: ReminderType
    ) {
        val application = getApplication()
        val packageInfo = application.packageManager.getPackageInfo(
            application.packageName,
            PackageManager.GET_PERMISSIONS
        )
        val permissions = packageInfo.requestedPermissions
        if (!permissions.contains(Manifest.permission.SET_ALARM)) {
            throw IllegalArgumentException("Require permission com.android.alarm.permission.SET_ALARM in manifest.xml")
        }
        when (reminderType) {
            is ReminderType.OneTime -> {
                NotificationExecutor.create()
                    .pushAfter(getApplication(), notificationContent, reminderType)
            }

            is ReminderType.Schedule -> {
                NotificationExecutor.create()
                    .pushInterval(getApplication(), notificationContent, reminderType)
            }
        }
    }

    fun pushNotification(notificationContent: NotificationContent) {
        notificationFactory.pushNotification(notificationContent)
    }

    fun cancelNotificationReminder(requestCodeReminder: Int) {
        NotificationExecutor.create().cancel(getApplication(), requestCodeReminder)
    }

    fun cancelNotification(notificationId: Int) {
        notificationFactory.cancel(notificationId)
    }

    fun isNotificationActive(notificationId: Int): Boolean {
        return notificationFactory.isActive(notificationId)
    }
}