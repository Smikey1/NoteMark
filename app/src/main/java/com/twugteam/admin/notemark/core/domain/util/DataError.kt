package com.twugteam.admin.notemark.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
    sealed interface Local : DataError {
        data object DiskFull: Local
        data class Unknown(val unknownError: String): Local
    }
}