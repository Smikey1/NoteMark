package com.twugteam.admin.notemark.features.auth.data

import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.data.networking.post
import com.twugteam.admin.notemark.core.domain.AuthInfo
import com.twugteam.admin.notemark.core.domain.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.features.auth.data.model.RegisterRequest
import com.twugteam.admin.notemark.features.auth.domain.AuthRepository
import io.ktor.client.HttpClient
import timber.log.Timber

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = ApiEndpoints.LOGIN_ENDPOINT,
            body = LoginRequest(email = email, password = password)
        )
        if (result is Result.Success) {
            sessionStorage.setAuthInfo(
                authInfo = AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    username = result.data.username,
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<RegisterRequest, Unit>(
            route = ApiEndpoints.REGISTER_ENDPOINT,
            body = RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
        return result
    }
}