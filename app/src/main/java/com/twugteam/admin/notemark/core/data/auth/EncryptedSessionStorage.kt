package com.twugteam.admin.notemark.core.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import com.twugteam.admin.notemark.core.domain.auth.AuthInfo
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber

class EncryptedSessionStorage(
    private val dataStore: DataStore<AuthInfoSerializable?>,
    private val context: Context
) : SessionStorage {
    override suspend fun getAuthInfo(): AuthInfo? = withContext(Dispatchers.IO) {
        return@withContext dataStore.data.firstOrNull()?.toAuthInfo()
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo?) {
        try {
            withContext(Dispatchers.IO) {
                if (authInfo == null) {
                    context.dataStoreFile("notemark_pref").delete()
                    return@withContext
                }
                dataStore.updateData {
                    authInfo.toAuthInfoSerializable()
                }
            }
        } catch (e: Exception) {
            Timber.tag("MyTag").e("setAuthInfo: ${e.localizedMessage}")
        }
    }
}