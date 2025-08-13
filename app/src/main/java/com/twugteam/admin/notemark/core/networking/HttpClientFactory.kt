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
                    Timber.tag("MyTag").d("bearer")
                    loadTokens {
                        Timber.tag("MyTag").d("loadTokens")
                        val authInfo = sessionStorage.getAuthInfo()
                        val bearerTokens = BearerTokens(
                            accessToken = authInfo?.accessToken ?: "",
                            refreshToken = authInfo?.refreshToken ?: ""
                        )
                        Timber.tag("MyTag")
                            .d("loadTokens: accessToken: ${bearerTokens.accessToken}")
                        Timber.tag("MyTag")
                            .d("loadTokens: refreshToken: ${bearerTokens.refreshToken}")
                        bearerTokens
                    }

                    refreshTokens {
                        Timber.tag("MyTag").d("refreshTokens")
                        val authInfo = sessionStorage.getAuthInfo()
                        Timber.tag("MyTag")
                            .d("refreshTokens sessionStorage refreshToken: ${authInfo?.refreshToken}")

                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = ApiEndpoints.REFRESH_TOKEN_ENDPOINT,
                            body = AccessTokenRequest(
                                refreshToken = authInfo?.refreshToken ?: "",
                            ),
                        ) {
                            markAsRefreshTokenRequest()
                        }

                        when (response) {
                            is Result.Error -> {
                                when (response.error) {
                                    NetworkError.Conflict -> {
                                        Timber.tag("MyTag").d("response error: Conflict")

                                    }

                                    NetworkError.NoInternet -> {
                                        Timber.tag("MyTag").d("response error: NoInternet")
                                    }

                                    NetworkError.PayloadTooLarge -> {
                                        Timber.tag("MyTag").d("response error: PayloadTooLarge")
                                    }

                                    NetworkError.RequestTimeout -> {
                                        Timber.tag("MyTag").d("response error: RequestTimeout")
                                    }

                                    NetworkError.Serialization -> {
                                        Timber.tag("MyTag").d("response error: Serialization")
                                    }

                                    NetworkError.ServerError -> {
                                        Timber.tag("MyTag").d("response error: ServerError")
                                    }

                                    NetworkError.TooManyRequests -> {
                                        Timber.tag("MyTag").d("response error: TooManyRequests")
                                    }

                                    NetworkError.UnAuthorized -> {
                                        Timber.tag("MyTag").d("response error: UnAuthorized")
                                    }

                                    NetworkError.Unknown -> {
                                        Timber.tag("MyTag").d("response error: Unknown")
                                    }
                                }
                                val bearerTokens = BearerTokens(
                                    accessToken = "",
                                    refreshToken = ""
                                )

                                //if the user is logged out and we enter username/password wrong
                                //does not force logout neither clearAuthInfo since it's already null
                                if(sessionStorage.getAuthInfo() != null) {
                                    //clear sessionTokens, username and userId after force logout
                                    sessionStorage.clearAuthInfo()
                                    //set refreshTokenExpired true to force logout
                                    sessionStorage.setRefreshTokenExpired(refreshTokenExpired = true)
                                }
                                Timber.tag("MyTag").d("else: ${bearerTokens.accessToken}")
                                bearerTokens
                            }

                            is Result.Success -> {
                                val newAuthInfo = AuthInfo(
                                    accessToken = response.data.accessToken,
                                    refreshToken = response.data.refreshToken,
                                    username = authInfo?.username ?: "",
                                    userId = authInfo?.userId ?: ""
                                )

                                sessionStorage.setAuthInfo(newAuthInfo)


                                val bearerTokens = BearerTokens(
                                    accessToken = newAuthInfo.accessToken,
                                    refreshToken = newAuthInfo.refreshToken
                                )
                                Timber.tag("MyTag")
                                    .d("refreshTokens: accessToken: ${bearerTokens.accessToken}")
                                Timber.tag("MyTag")
                                    .d("refreshTokens: refreshToken: ${bearerTokens.refreshToken}")
                                bearerTokens
                            }
                        }
                    }
                }
            }
        }
    }
}