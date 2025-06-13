package com.twugteam.admin.notemark.features.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val refreshToken: String,
    val accessToken: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: String
)
