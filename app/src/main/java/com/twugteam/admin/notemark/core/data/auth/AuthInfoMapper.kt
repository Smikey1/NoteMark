@file:OptIn(InternalSerializationApi::class)

package com.twugteam.admin.notemark.core.data.auth

import com.twugteam.admin.notemark.core.domain.AuthInfo
import kotlinx.serialization.InternalSerializationApi


fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}