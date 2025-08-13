package com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource

import com.twugteam.admin.notemark.core.database.sync.SyncEntity
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result

interface LocalSyncDataSource {
    suspend fun upsertSyncOperation(syncEntity: SyncEntity): Result<Unit, DataError.Local>
    suspend fun getAllSyncOperationsByUserId(userId: String): List<SyncEntity>?
    suspend fun deleteSyncOperation(userId: String, noteId: String): Result<Unit, DataError.Local>
    suspend fun getSyncEntityByNoteId(noteId: String): SyncEntity?
}