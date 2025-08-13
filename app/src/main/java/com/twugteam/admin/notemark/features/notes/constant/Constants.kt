package com.twugteam.admin.notemark.features.notes.constant

import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval
import java.util.concurrent.TimeUnit

object Constants {
    val syncingIntervalList = listOf<SyncInterval>(
        SyncInterval(null, "Manual only", timeUnit = null),
        SyncInterval(15, " minutes", timeUnit = TimeUnit.MINUTES),
        SyncInterval(30, " minutes", timeUnit = TimeUnit.MINUTES),
        SyncInterval(1, " hour",  timeUnit = TimeUnit.HOURS)
    )

    const val SYNC_INTERVAL_WORK_NAME = "SyncingWithInterval"
    const val MANUAL_SYNC_WORK_NAME = "ManualSyncing"
    const val SYNC_INTERVAL_WORK_TAG = "SYNCING_TAG"
    const val MANUAL_WORK_TAG = "MANUAL_TAG"

    const val SYNC_INTERVAL = "SyncInterval"
    const val SYNC_TEXT = "SyncText"
    const val SYNC_TIME_UNIT = "SyncTimeUnit"
    const val LAST_SYNC_TIMESTAMP = "LastSyncTimestamp"

    const val REMOTE_NOTES_FETCH = "RemoteNotesFetch"

    const val REFRESH_TOKEN = "RefreshToken"

}