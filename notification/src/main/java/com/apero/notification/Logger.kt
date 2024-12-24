package com.apero.notification

import android.util.Log

/**
 * Created by KO Huyn on 23/12/2024.
 */
internal object Logger {
    fun d(message: String) {
        Log.d("NotificationSDK", message)
    }

    fun i(message: String) {
        Log.i("NotificationSDK", message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e("NotificationSDK", message, throwable)
    }
}