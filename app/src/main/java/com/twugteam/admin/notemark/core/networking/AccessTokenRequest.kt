package com.twugteam.admin.notemark.core.networking

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val username: String
)
