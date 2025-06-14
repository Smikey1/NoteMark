package com.twugteam.admin.notemark.features.auth.domain

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.features.auth.data.model.RegisterRequest

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(registerRequest: RegisterRequest): EmptyResult<DataError.Network>
}