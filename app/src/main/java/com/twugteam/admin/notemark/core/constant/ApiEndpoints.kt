package com.twugteam.admin.notemark.core.constant

import com.twugteam.admin.notemark.BuildConfig


object ApiEndpoints {
    const val NOTEMARK_API_BASE_URL = BuildConfig.NOTEMARK_API_BASE_URL
    const val EMAIL = BuildConfig.EMAIL

    // Auth Endpoints
    const val LOGIN_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/login"
    const val REGISTER_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/register"
    const val REFRESH_TOKEN_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/refresh"
    const val LOGOUT_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/logout"


    // Note Endpoints
    const val NOTES_ENDPOINT = BuildConfig.NOTES_ENDPOINT


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
