package com.twugteam.admin.notemark.core.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.twugteam.admin.notemark.core.domain.auth.AuthInfo
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.constant.Constants.REFRESH_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

class EncryptedSessionStorage(
    private val syncDataStore: DataStore<AuthInfoSerializable?>,
    private val refreshTokenDataStore: DataStore<Preferences>,
    private val context: Context
) : SessionStorage {
    private val refreshTokenBoolean = booleanPreferencesKey(REFRESH_TOKEN)

    override suspend fun getAuthInfo(): AuthInfo? = withContext(Dispatchers.IO) {
        return@withContext syncDataStore.data.firstOrNull()?.toAuthInfo()
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo?) {
        try {
            withContext(Dispatchers.IO) {
                if (authInfo == null) {
                    context.dataStoreFile("notemark_pref").delete()
                    return@withContext
                }
                syncDataStore.updateData {
                    authInfo.toAuthInfoSerializable()
                }
            }
        } catch (e: Exception) {
            Timber.tag("MyTag").e("setAuthInfo: ${e.localizedMessage}")
        }
    }

    override suspend fun setRefreshTokenExpired(refreshTokenExpired: Boolean) {
        refreshTokenDataStore.edit { preferences ->
            try {
                preferences[refreshTokenBoolean] = refreshTokenExpired
            } catch (e: Exception) {
                Timber.tag("MyTag").e("setRefreshToken: ${e.localizedMessage}")
            }
        }
    }

    override fun getRefreshTokenExpired(): Flow<Boolean> =
        refreshTokenDataStore.data.map { preferences ->
            val refreshTokenBoolean = preferences[refreshTokenBoolean]
            //refreshTokenBoolean ?: false
            //if refreshTokenBoolean = false -> false == true return false
            //if refreshTokenBoolean is null -> null == true return false
            //if refreshTokenBoolean is true -> true == true return true
            refreshTokenBoolean == true
        }

    //clearing refreshToken only and username
    override suspend fun clearAuthInfo(): Result<Unit, DataError.Local> {
        return try {
            setAuthInfo(null)
            Timber.tag("MyTag").d("clearAuthInfo: success")
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.tag("MyTag").e("clearAuthInfo: ${e.localizedMessage}")
            Result.Error(error = DataError.Local.Unknown(unknownError = e.message.toString()))
        }
    }
}