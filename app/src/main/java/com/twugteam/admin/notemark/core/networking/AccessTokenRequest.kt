package com.twugteam.admin.notemark.core.networking

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AccessTokenRequest(
    @SerialName("refreshToken") val refreshToken: String,
)
