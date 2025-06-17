package com.twugteam.admin.notemark.core.networking

import com.twugteam.admin.notemark.BuildConfig
import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.domain.auth.AuthInfo
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
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
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    private val refreshClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            header("X-User-Email", ApiEndpoints.EMAIL)
        }
    }

    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Timber.tag("ApiRequest").d(message)
                        }
                    }
                    level = LogLevel.ALL
                }
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
                        val response = refreshClient.post<AccessTokenRequest, AccessTokenResponse>(
                            route = ApiEndpoints.REFRESH_TOKEN_ENDPOINT,
                            body = AccessTokenRequest(
                                refreshToken = authInfo?.refreshToken ?: "",
                                username = authInfo?.username ?: ""
                            )
                        )
                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = authInfo?.refreshToken ?: "",
                                username = authInfo?.username ?: ""
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