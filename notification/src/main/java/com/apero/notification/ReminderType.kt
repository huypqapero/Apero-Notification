package com.apero.notification

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.parcelize.Parcelize
import java.util.concurrent.TimeUnit

abstract class ReminderType : Parcelable {
    @Parcelize
    data class OneTime(
        val notifyTime: Long,
        val notifyTimeUnit: TimeUnit
    ) : ReminderType()

    @Parcelize
    data class Schedule(
        val notifyTimeHour: Int,
        val notifyTimeMinute: Int,
        @IntRange(from = 1)
        val stepDay: Int = 1,
        @IntRange(from = 0)
        val minimumTimeCurrentToNotify: Long = 0
    ) : ReminderType()
}