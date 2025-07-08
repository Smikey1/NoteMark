package com.twugteam.admin.notemark.core.domain.util

import com.twugteam.admin.notemark.core.networking.NetworkError

fun NetworkError.toDataErrorNetwork(): DataError.Network = when (this) {
    NetworkError.Conflict -> DataError.Network.CONFLICT
    NetworkError.NoInternet -> DataError.Network.NO_INTERNET
    NetworkError.PayloadTooLarge -> DataError.Network.PAYLOAD_TOO_LARGE
    NetworkError.RequestTimeout -> DataError.Network.REQUEST_TIMEOUT
    NetworkError.Serialization -> DataError.Network.SERIALIZATION
    NetworkError.ServerError -> DataError.Network.SERVER_ERROR
    NetworkError.TooManyRequests -> DataError.Network.TOO_MANY_REQUESTS
    NetworkError.UnAuthorized -> DataError.Network.UNAUTHORIZED
    NetworkError.Unknown -> DataError.Network.UNKNOWN
}