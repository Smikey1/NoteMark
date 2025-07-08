package com.twugteam.admin.notemark.core.networking


import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException


suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParams: Map<String, Any?> = mapOf()
): Result<Response, NetworkError> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParams.forEach {
                parameter(it.key,it.value)
            }
        }
    }
}


suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParams: Map<String, Any?> = mapOf()
): Result<Response, NetworkError> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParams.forEach {
                parameter(it.key,it.value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body:Request,
    //to add  markAsRefreshTokenRequest() in refreshTokens{}
    crossinline builder: HttpRequestBuilder.() -> Unit = {}

): Result<Response, NetworkError> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
            builder()
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    body:Request
): Result<Response, NetworkError> {
    return safeCall {
        put {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(NetworkError.NoInternet)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(NetworkError.Serialization)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(NetworkError.Unknown)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(NetworkError.UnAuthorized)
        408 -> Result.Error(NetworkError.RequestTimeout)
        409 -> Result.Error(NetworkError.Conflict)
        413 -> Result.Error(NetworkError.PayloadTooLarge)
        429 -> Result.Error(NetworkError.TooManyRequests)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> Result.Error(NetworkError.Unknown)
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(ApiEndpoints.NOTEMARK_API_BASE_URL) -> route
        route.startsWith("/") -> ApiEndpoints.NOTEMARK_API_BASE_URL + route
        else -> ApiEndpoints.NOTEMARK_API_BASE_URL + "/$route"
    }
}