package com.apero.notification.sample.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.apero.notification.NotificationContent
import com.apero.notification.getNotificationManager
import com.apero.notification.sample.R
import kotlinx.parcelize.Parcelize

/**
 * Created by KO Huyn on 23/12/2024.
 */
@Parcelize
data class DefaultNotificationType(
    val title: String = "Test Sample", val description: String = "This is a sample notification"
) : NotificationContent(REQUEST_CODE_PUSH_NOTIFICATION) {
    companion object {
        const val NOTIFICATION_ID = 123
        const val REQUEST_CODE_PUSH_NOTIFICATION = 1
    }

    override fun getNotifyId(): Int {
        return NOTIFICATION_ID
    }

    override fun getBy(context: Context): Notification? {
        val notificationManager = context.getNotificationManager()

        val builder = NotificationCompat.Builder(context, "NOTIFICATION_CHANNEL")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(title)
            .setContentInfo(description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(false)
            .setAutoCancel(true)
            .setShowWhen(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("NOTIFICATION_CHANNEL")
            val channel = NotificationChannel(
                "NOTIFICATION_CHANNEL",
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()
    }
}