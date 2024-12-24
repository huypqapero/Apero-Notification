# Apero-Notification
## Implementation
```kotlin
implementation("apero-inhouse:notification:1.0.0")
```
## How to use:
### In Manifest:
```kotlin
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
    <uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" /> //If using Notification FullScreen
```
### In application:
```kotlin
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationSDK.install(this)
    }
}
```
### Create Notification:
```kotlin
@Parcelize
data class SampleNotificationType(
    val title: String = "Test Sample", val description: String = "This is a sample notification"
) : NotificationContent(REQUEST_CODE_PUSH_NOTIFICATION) {
    companion object {
        private const val NOTIFICATION_ID = 123
        private const val REQUEST_CODE_PUSH_NOTIFICATION = 1
        private const val CHANNEL = "NOTIFICATION_CHANNEL"
    }

    override fun getNotifyId(): Int {
        return NOTIFICATION_ID
    }
    override fun getBy(context: Context): Notification {
        //TODO handle in app with spec. THIS IS A SAMPLE
        val notificationManager = context.getNotificationManager()

        val builder = NotificationCompat.Builder(context, CHANNEL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(title)
            .setContentInfo(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
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
```
### Push Notification OneTime After:
```kotlin
NotificationSDK.pushNotificationAfter(
    SampleNotificationType(),
    ReminderType.OneTime(15, TimeUnit.MINUTES)
)
```
### Push Notification Schedule:
```kotlin
NotificationSDK.pushNotificationAfter(
    SampleNotificationType(),
    ReminderType.Schedule(hourOfDay, minute)
)
```
### Push Notification Now:
```kotlin
NotificationSDK.pushNotification(SampleNotificationType())
```
### Cancel Push Notification Schedule
```kotlin
NotificationSDK.cancelNotificationReminder(DefaultNotificationType.REQUEST_CODE_PUSH_NOTIFICATION)
```
### Clear Notification In Statusbar
```kotlin
if (NotificationSDK.isNotificationActive(DefaultNotificationType.NOTIFICATION_ID)) {
    NotificationSDK.cancelNotification(DefaultNotificationType.NOTIFICATION_ID)
}
```
### Using With Notification Fullscreen:
```kotlin
class SampleFullscreenActivity : NotificationFullscreenActivity() {
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
```
```kotlin
<activity
    android:name="com.apero.notification.sample.notification.SampleFullscreenActivity"
    android:excludeFromRecents="true"
    android:exported="false"
    android:launchMode="singleInstance"
    android:noHistory="true" />
```
```kotlin
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
            //this is a set fullscreen notification
            .setFullScreenIntent(
                fullScreenIntent(context),
                true
            )
            //-----end------
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
        intent.putExtra(DefaultFullscreenActivity.ARG_NOTIFICATION_CONTENT, this)
        return PendingIntent.getActivity(
            context,
            -1,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
```
