package com.apero.notification.sample.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
data class FullscreenNotificationContent(
    val title: String = "This is sample",
    val content: String = "This is sample description"
) : NotificationContent(REQUEST_CODE_PUSH_NOTIFICATION) {
    companion object {
        private const val NOTIFICATION_ID = 111
        private const val REQUEST_CODE_PUSH_NOTIFICATION = 2
        private const val CHANNEL = "NOTIFICATION_CHANNEL"
    }

    override fun getNotifyId(): Int {
        return NOTIFICATION_ID
    }

    override fun getBy(context: Context): Notification {
        val notificationManager = context.getNotificationManager()

        val builder = NotificationCompat.Builder(context, CHANNEL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(title)
            .setContentInfo(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(false)
            .setAutoCancel(true)
            .setShowWhen(false)
            .setFullScreenIntent(
                fullScreenIntent(context),
                true
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL)
            val channel = NotificationChannel(
                CHANNEL,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()

    }

    private fun fullScreenIntent(context: Context): PendingIntent {
        val intent = Intent(context, DefaultFullscreenActivity::class.java)
        intent.putExtra("ARG", this)
        return PendingIntent.getActivity(
            context,
            -1,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}