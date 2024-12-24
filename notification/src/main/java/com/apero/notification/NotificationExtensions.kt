package com.apero.notification

import android.app.NotificationManager
import android.content.Context
/**
 * Created by KO Huyn on 21/09/2023.
 */

fun Context.getNotificationManager() =
    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager