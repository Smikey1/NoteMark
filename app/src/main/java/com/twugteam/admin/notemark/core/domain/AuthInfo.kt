package com.twugteam.admin.notemark.core.domain

data class AuthInfo (
    val accessToken: String,
    val refreshToken: String,
    val username: String
)