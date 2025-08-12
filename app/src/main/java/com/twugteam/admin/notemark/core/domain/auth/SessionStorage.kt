package com.twugteam.admin.notemark.core.domain.auth

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    suspend fun getAuthInfo(): AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
    suspend fun setRefreshTokenExpired(refreshTokenExpired: Boolean)
    fun getRefreshTokenExpired(): Flow<Boolean>
    suspend fun clearAuthInfo(): Result<Unit, DataError.Local>
}