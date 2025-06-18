package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NotePendingSyncDao {
    // For Created Note Pending Sync
    @Query("Select * from creatednotependingsyncentity where username = :username")
    suspend fun getAllUserCreatedNotePendingSyncEntities(username: String): List<CreatedNotePendingSyncEntity>

    @Query("select * from creatednotependingsyncentity where noteId = :noteId")
    suspend fun getCreatedNotePendingSyncEntityById(noteId: String): CreatedNotePendingSyncEntity?

    @Upsert
    suspend fun upsertCreatedNotePendingEntity(notePendingSyncEntity: CreatedNotePendingSyncEntity)

    @Query("delete from creatednotependingsyncentity where noteId = :noteId")
    suspend fun deleteCreatedNotePendingSyncEntityById(noteId: String)



    // For Deleted Note Pending Sync
    @Query("select * from deletednotependingsyncentity where username= :username")
    suspend fun getAllUserDeletedNotePendingSyncEntities(username: String): List<DeletedNotePendingSyncEntity>

    @Upsert
    suspend fun upsertDeletedNotePendingSyncEntity(deletedNotePendingSyncEntity: DeletedNotePendingSyncEntity)

    @Query("delete from deletednotependingsyncentity where noteId = :noteId")
    suspend fun deleteDeletedNotePendingSyncEntity(noteId: String)

}