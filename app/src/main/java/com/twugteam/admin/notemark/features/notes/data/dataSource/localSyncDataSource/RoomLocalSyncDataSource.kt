package com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource

import android.database.sqlite.SQLiteFullException
import com.twugteam.admin.notemark.core.database.sync.SyncDao
import com.twugteam.admin.notemark.core.database.sync.SyncEntity
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import timber.log.Timber

class RoomLocalSyncDataSource(
    private val syncDao: SyncDao
) : LocalSyncDataSource {
    override suspend fun upsertSyncOperation(syncEntity: SyncEntity): Result<Unit, DataError.Local> {
        return try {
            syncDao.upsertSyncOperation(syncEntity)
            Timber.Forest.tag("MyTag").d("upsertSyncOperation: success : $syncEntity")
            Result.Success(Unit)
        } catch (_: SQLiteFullException) {
            Timber.Forest.tag("MyTag").e("upsertSyncOperation: error")
            Result.Error(DataError.Local.DiskFull)
        }
    }

    override suspend fun getAllSyncOperationsByUserId(userId: String): List<SyncEntity>? {
        return if (userId.isNotEmpty()) syncDao.getAllSyncByUserId(userId = userId) else
            null
    }

    override suspend fun deleteSyncOperation(
        userId: String,
        noteId: String
    ): Result<Unit, DataError.Local> {
        return try {
            syncDao.deleteSyncOperation(userId = userId, noteId = noteId)
            Timber.Forest.tag("MyTag").d("deleteSyncOperation: success :")
            Result.Success(Unit)
        } catch (_: SQLiteFullException) {
            Timber.Forest.tag("MyTag").e("deleteSyncOperation: error")
            Result.Error(DataError.Local.DiskFull)
        }
    }

    override suspend fun getSyncEntityByNoteId(noteId: String): SyncEntity?{
        return try {
            val syncEntity = syncDao.getSyncEntityByNoteId(noteId = noteId)
            Timber.Forest.tag("MyTag").d("getSyncEntityByNoteId: $syncEntity")
            syncEntity
        }catch (e: Exception){
            Timber.Forest.tag("MyTag").d("getSyncEntityByNoteId: error: ${e.localizedMessage}")
            null
        }
    }

}