package com.twugteam.admin.notemark.core.database.notes

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun insertAllNotes(notes: List<NoteEntity>)

    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Query("select * from NoteEntity where id = :id")
    suspend fun getNoteById(id: String): NoteEntity

    @Query("select * from NoteEntity order by lastEditedAt desc")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity")
    fun getPagingNotes(): PagingSource<Int, NoteEntity>

    @Query("delete from NoteEntity where id = :id")
    suspend fun deleteNoteById(id: String)

    @Query("DELETE FROM NoteEntity")
    suspend fun clearNotes()
}