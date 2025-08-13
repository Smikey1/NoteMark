package com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.syncInterval

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.constant.Constants.LAST_SYNC_TIMESTAMP
import com.twugteam.admin.notemark.features.notes.constant.Constants.SYNC_INTERVAL
import com.twugteam.admin.notemark.features.notes.constant.Constants.SYNC_TEXT
import com.twugteam.admin.notemark.features.notes.constant.Constants.SYNC_TIME_UNIT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SyncIntervalDataStoreDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : SyncIntervalDataStoreDataSource {
    private val syncInterval = longPreferencesKey(SYNC_INTERVAL)
    private val syncText = stringPreferencesKey(SYNC_TEXT)
    private val syncTimeUnit = stringPreferencesKey(SYNC_TIME_UNIT)

    private val lastSyncTimestamp = longPreferencesKey(LAST_SYNC_TIMESTAMP)

    override suspend fun saveInterval(
        interval: Long?,
        text: String,
        timeUnit: TimeUnit?
    ): Result<Unit, DataError.Local> {
        return try {
            dataStore.edit { preferences ->
                if (interval != null && timeUnit != null) {
                    preferences[syncInterval] = interval
                    preferences[syncTimeUnit] = timeUnit.toString()
                } else {
                    preferences.remove(syncInterval)
                    preferences.remove(syncTimeUnit)
                }
                preferences[syncText] = text
            }
            Timber.tag("MyTag").d("saveInterval: success $interval, $syncText. $syncTimeUnit")
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.tag("MyTag").e("saveInterval : error: ${e.localizedMessage}")
            Result.Error(DataError.Local.DiskFull)
        }
    }

    override fun getInterval(): Flow<Triple<Long?, String, TimeUnit?>> =
        dataStore.data.map { preferences ->
            //get them as string
            val intervalString = preferences[syncInterval]
            val timeUnitString = preferences[syncTimeUnit]

            val syncText = preferences[syncText]

            //convert them to their types
            val interval = intervalString
            val timeUnit = try {
                timeUnitString?.let { TimeUnit.valueOf(it) }
            } catch (e: Exception) {
                Timber.tag("MyTag").e("getInterval: timeUnitError ${e.localizedMessage}")
                null
            }
            Timber.tag("MyTag").d("getInterval: interval: $interval timeUnit: $timeUnit")
            Triple(interval, syncText ?: "Manual only", timeUnit)
        }

    override suspend fun saveLastSyncTimestamp(): Result<Unit, DataError.Local> {
        return try {
            val nowMillis = System.currentTimeMillis()
            dataStore.edit { preferences ->
                preferences[lastSyncTimestamp] = nowMillis
            }
            Timber.tag("MyTag").d("saveLastSyncTimestamp: success")
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.tag("MyTag").e("saveLastSyncTimestamp: error: ${e.localizedMessage}")
            Result.Error(DataError.Local.DiskFull)
        }
    }

    //after logOut reset last sync timeStamp
    override suspend fun resetTimeStamp() {
        try {
            dataStore.edit { preferences->
                preferences.remove(lastSyncTimestamp)
                Timber.tag("MyTag").d("resetTimeStamp: success")
            }
        }catch (e: Exception){
            Timber.tag("MyTag").e("resetTimeStamp: error: ${e.localizedMessage}")
        }
    }

    override fun getLastSyncTimestamp(): Flow<Long> =
        dataStore.data.map { preferences ->
            val timeStamp = preferences[lastSyncTimestamp] ?: -1L
            Timber.tag("MyTag").d("getLastSyncTimestamp(): $timeStamp")
            timeStamp
        }
}