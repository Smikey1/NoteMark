package com.twugteam.admin.notemark.core.data.auth

import androidx.datastore.core.DataStore
import com.twugteam.admin.notemark.core.domain.auth.AuthInfo
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class EncryptedSessionStorage(
    private val dataStore: DataStore<AuthInfoSerializable?>,
) : SessionStorage {
    override suspend fun getAuthInfo(): AuthInfo? = withContext(Dispatchers.IO) {
        Timber.tag("MyTag").d("getAuthInfo")
        return@withContext dataStore.data.firstOrNull()?.toAuthInfo()
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo?) {
        Timber.tag("MyTag").d("setAuthInfo")
        withContext(Dispatchers.IO) {
            if (authInfo == null) {
                dataStore.updateData { null }
            }
            dataStore.updateData {
                authInfo?.toAuthInfoSerializable()
            }
        }
    }
}