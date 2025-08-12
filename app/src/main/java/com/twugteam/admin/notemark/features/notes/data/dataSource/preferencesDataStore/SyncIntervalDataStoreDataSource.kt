package com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

interface SyncIntervalDataStoreDataSource {
    suspend fun saveInterval(
        interval: Long?,
        text: String,
        timeUnit: TimeUnit?
    ): Result<Unit, DataError.Local>

    fun getInterval(): Flow<Triple<Long?, String, TimeUnit?>>

    suspend fun saveLastSyncTimestamp(): Result<Unit, DataError.Local>
    fun getLastSyncTimestamp(): Flow<Long>
    suspend fun resetTimeStamp()
}