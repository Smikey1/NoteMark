package com.twugteam.admin.notemark.core.data.auth

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

//Prevent class and field renaming during obfuscated (release) builds
@Keep
@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)
