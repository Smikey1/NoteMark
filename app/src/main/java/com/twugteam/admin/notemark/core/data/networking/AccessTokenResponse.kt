package com.twugteam.admin.notemark.core.data.networking

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)
