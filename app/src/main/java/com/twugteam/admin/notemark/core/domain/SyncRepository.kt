package com.twugteam.admin.notemark.core.domain

import java.util.concurrent.TimeUnit

interface SyncRepository {
    suspend fun manualSync()
    suspend fun syncWithInterval(interval: Long?, timeUnit: TimeUnit?)
}