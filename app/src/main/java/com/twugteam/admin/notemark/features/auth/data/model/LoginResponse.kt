package com.twugteam.admin.notemark.features.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val refreshToken: String,
    val accessToken: String,
    val username: String,
)
