package com.twugteam.admin.notemark.core.data.auth

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
