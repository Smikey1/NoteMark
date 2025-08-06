package com.twugteam.admin.notemark.features.notes.data.dataSource

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.SyncIntervalDataSource
import com.twugteam.admin.notemark.features.notes.domain.SyncIntervalDataStore
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class SyncIntervalDataStoreImpl(
    private val syncIntervalDataSource: SyncIntervalDataSource
) : SyncIntervalDataStore {
    override suspend fun saveInterval(
        interval: Long?,
        text: String,
        timeUnit: TimeUnit?
    ): Result<Unit, DataError.Local> {
        return syncIntervalDataSource.saveInterval(
            interval = interval,
            text = text,
            timeUnit = timeUnit
        )
    }

    override fun getInterval(): Flow<Triple<Long?, String, TimeUnit?>> {
        return syncIntervalDataSource.getInterval()
    }
}