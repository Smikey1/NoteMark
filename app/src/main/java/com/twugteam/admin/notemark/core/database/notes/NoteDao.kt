package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Upsert
    suspend fun upsertNotes(notesEntities: List<NoteEntity>)

    @Query("select * from noteentity where id = :id")
    fun getNoteById(id: String): Flow<NoteEntity>
    @Query("select * from noteentity order by lastEditedAt desc")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("delete from noteentity where id = :id")
    fun deleteNoteById(id: String)
}