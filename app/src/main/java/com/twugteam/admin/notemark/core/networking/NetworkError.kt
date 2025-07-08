package com.twugteam.admin.notemark.core.networking

import com.twugteam.admin.notemark.core.domain.util.Error

sealed interface NetworkError: Error {
    object RequestTimeout : NetworkError
    object UnAuthorized : NetworkError
    object Conflict : NetworkError
    object TooManyRequests : NetworkError
    object NoInternet : NetworkError
    object PayloadTooLarge : NetworkError
    object ServerError : NetworkError
    object Serialization : NetworkError
    object Unknown : NetworkError
}