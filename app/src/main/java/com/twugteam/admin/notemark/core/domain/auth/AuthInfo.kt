package com.twugteam.admin.notemark.core.domain.auth

data class AuthInfo (
    val accessToken: String,
    val refreshToken: String,
    val username: String
)