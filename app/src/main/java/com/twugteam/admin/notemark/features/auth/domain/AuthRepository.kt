package com.twugteam.admin.notemark.features.auth.domain

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
}