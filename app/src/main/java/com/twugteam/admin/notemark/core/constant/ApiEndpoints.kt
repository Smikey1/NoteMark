package com.twugteam.admin.notemark.core.constant

import com.twugteam.admin.notemark.BuildConfig


object ApiEndpoints {
    const val NOTEMARK_API_BASE_URL = BuildConfig.NOTEMARK_API_BASE_URL
    const val EMAIL = BuildConfig.EMAIL

    // Auth Endpoints
    const val LOGIN_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/login"
    const val REGISTER_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/register"
    const val REFRESH_TOKEN_ENDPOINT = BuildConfig.AUTH_ENDPOINT + "/refresh"


    // Note Endpoints
    const val NOTES_ENDPOINT = BuildConfig.NOTES_ENDPOINT

}
