package com.twugteam.admin.notemark.core.domain.auth

interface SessionStorage {
    suspend fun getAuthInfo(): AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
}