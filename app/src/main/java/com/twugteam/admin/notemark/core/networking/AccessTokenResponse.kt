package com.twugteam.admin.notemark.core.networking

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
