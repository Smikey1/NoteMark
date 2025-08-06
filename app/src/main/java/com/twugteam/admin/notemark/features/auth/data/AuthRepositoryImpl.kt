package com.twugteam.admin.notemark.features.auth.data

import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.domain.auth.AuthInfo
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.core.domain.util.mapToResult
import com.twugteam.admin.notemark.core.domain.util.toDataErrorNetwork
import com.twugteam.admin.notemark.core.networking.post
import com.twugteam.admin.notemark.features.auth.data.model.LoginRequest
import com.twugteam.admin.notemark.features.auth.data.model.LoginResponse
import com.twugteam.admin.notemark.features.auth.data.model.RegisterRequest
import com.twugteam.admin.notemark.features.auth.domain.AuthRepository
import io.ktor.client.HttpClient
import timber.log.Timber
import java.util.UUID

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
        ).mapToResult(
            success = { it },
            networkError = { it.toDataErrorNetwork() }
        )

        if (result is Result.Success) {
            val userId = UUID.nameUUIDFromBytes(email.toByteArray(Charsets.UTF_8)).toString()
            sessionStorage.setRefreshTokenExpired(refreshTokenExpired = false)
            Timber.tag("userIdValue").d("userIdValue: $userId")
            sessionStorage.setAuthInfo(
                authInfo = AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    username = result.data.username,
                    userId = userId
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
        ).mapToResult(
            success = {},
            networkError = { it.toDataErrorNetwork() }
        )
        return result
    }
}