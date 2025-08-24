package com.twugteam.admin.notemark.core.constant

object NotificationConstants {

    //google codeLab Background Work with WorkManager
    //Name of Notification Channel for verbose notifications of background work
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    const val NOTIFICATION_TITLE = "Sync start"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1
}