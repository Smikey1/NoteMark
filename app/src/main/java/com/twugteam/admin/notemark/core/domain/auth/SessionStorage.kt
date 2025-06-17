package com.twugteam.admin.notemark.core.domain.auth

interface SessionStorage {
    suspend fun getAuthInto(): AuthInfo?
    suspend fun setAuthInfo(authInfo: AuthInfo?)
}