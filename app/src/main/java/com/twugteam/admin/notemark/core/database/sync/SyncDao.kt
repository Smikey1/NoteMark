package com.twugteam.admin.notemark.core.database.sync

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SyncDao {
    @Upsert
    suspend fun upsertSyncOperation(syncEntity: SyncEntity)

    @Query("SELECT * FROM SyncEntity WHERE userId = :userId")
    suspend fun getAllSyncByUserId(userId: String): List<SyncEntity>

    @Query("DELETE FROM SyncEntity WHERE userId = :userId AND noteId = :noteId")
    suspend fun deleteSyncOperation(userId: String, noteId: String)

    @Query("SELECT * FROM SyncEntity WHERE noteId = :noteId LIMIT 1")
    suspend fun getSyncEntityByNoteId(noteId: String): SyncEntity?

    @Query("DELETE FROM SyncEntity WHERE userId = :userId")
    suspend fun clearSync(userId: String)

}