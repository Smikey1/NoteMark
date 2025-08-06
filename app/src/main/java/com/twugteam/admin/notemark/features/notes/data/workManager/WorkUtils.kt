package com.twugteam.admin.notemark.features.notes.data.workManager

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.constant.ApiEndpoints.CHANNEL_ID
import com.twugteam.admin.notemark.core.constant.ApiEndpoints.NOTIFICATION_ID
import com.twugteam.admin.notemark.core.constant.ApiEndpoints.NOTIFICATION_TITLE
import com.twugteam.admin.notemark.core.constant.ApiEndpoints.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.twugteam.admin.notemark.core.constant.ApiEndpoints.VERBOSE_NOTIFICATION_CHANNEL_NAME
import timber.log.Timber

//google codeLab Background Work with WorkManager
object WorkUtils {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun makeStatusNotification(message: String, context: Context) {

        Timber.tag("SyncingWorker").d("show notification")
        //create notification channel if necessary
        createNotificationChannel(context)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.sync)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel(context: Context){
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
            val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
            //since it's Syncing only we changed Importance_High to Importance_low
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }
}