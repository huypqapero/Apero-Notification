package com.apero.notification.sample

import android.app.Application
import com.apero.notification.NotificationSDK

/**
 * Created by KO Huyn on 23/12/2024.
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationSDK.install(this)
    }
}