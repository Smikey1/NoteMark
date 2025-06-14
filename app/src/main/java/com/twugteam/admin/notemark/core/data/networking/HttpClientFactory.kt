@file:OptIn(InternalSerializationApi::class)

package com.twugteam.admin.notemark.core.data.networking

import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.domain.AuthInfo
import com.twugteam.admin.notemark.core.domain.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("ApiRequest").d(message)
                    }

                }
                level = LogLevel.ALL
            }


            defaultRequest {
                contentType(ContentType.Application.Json)
                header("X-User-Email", ApiEndpoints.EMAIL)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val authInfo = sessionStorage.getAuthInto()
                        BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = authInfo?.refreshToken ?: ""
                        )
                    }

                    refreshTokens {
                        val authInfo = sessionStorage.getAuthInto()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = ApiEndpoints.REFRESH_TOKEN_ENDPOINT,
                            body = AccessTokenRequest(
                                refreshToken = authInfo?.refreshToken ?: "",
                                userId = authInfo?.userId ?: ""
                            )
                        )
                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = authInfo?.refreshToken ?: "",
                                userId = authInfo?.userId ?: ""
                            )
                            sessionStorage.setAuthInfo(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}