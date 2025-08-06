package com.twugteam.admin.notemark.core.domain.auth

import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    suspend fun getAuthInfo(): AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
    suspend fun setRefreshTokenExpired(refreshTokenExpired: Boolean)
    fun getRefreshTokenExpired(): Flow<Boolean>
}