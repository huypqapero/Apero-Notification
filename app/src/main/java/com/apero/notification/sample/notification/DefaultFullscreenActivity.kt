package com.apero.notification.sample.notification

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.apero.notification.fullscreen.NotificationFullscreenActivity
import com.apero.notification.sample.MainActivity
import com.apero.notification.sample.R

/**
 * Created by KO Huyn on 23/12/2024.
 */
class DefaultFullscreenActivity : NotificationFullscreenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivty_fullscreen_default)
        findViewById<Button>(R.id.btnCTA).setOnClickListener {
            navigateTo(MainActivity::class.java)
        }
    }

    override fun updateDateTimeDisplayFullScreen(dateTimeFullScreen: TimeFullScreen) {
        findViewById<TextView>(R.id.tvDate).text = dateTimeFullScreen.dateDisplay
        findViewById<TextView>(R.id.tvHour).text = dateTimeFullScreen.hourDisplay
        findViewById<TextView>(R.id.tvMinute).text = dateTimeFullScreen.minuteDisplay
    }
}