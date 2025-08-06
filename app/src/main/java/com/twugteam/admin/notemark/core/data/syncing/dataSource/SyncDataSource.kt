package com.twugteam.admin.notemark.core.data.syncing.dataSource

import java.util.concurrent.TimeUnit

interface SyncDataSource {
    suspend fun manualSync()
    suspend fun syncWithInterval(interval: Long?, timeUnit: TimeUnit?)
    suspend fun cancelAllWork():  Boolean
}