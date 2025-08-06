package com.twugteam.admin.notemark.core.data.syncing

import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSource
import com.twugteam.admin.notemark.core.domain.SyncRepository
import java.util.concurrent.TimeUnit

class SyncRepositoryImpl(
    private val syncDataSource: SyncDataSource
): SyncRepository {
    override suspend fun manualSync() {
        syncDataSource.manualSync()
    }

    override suspend fun syncWithInterval(interval: Long?, timeUnit: TimeUnit?) {
        syncDataSource.syncWithInterval(interval = interval, timeUnit = timeUnit)
    }
}