package com.twugteam.admin.notemark.core.database.notes

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NoteDao {
    //upsert(insert or update if exist) list of notes
    @Upsert
    suspend fun upsertAllNotes(notes: List<NoteEntity>)

    //insert single note
    @Upsert
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Query("select * from NoteEntity where id = :id")
    suspend fun getNoteById(id: String): NoteEntity

    //get all notes in Paging via RemoteMediator
    @Query("SELECT * FROM NoteEntity ORDER BY lastEditedAt DESC")
    fun getPagingNotes(): PagingSource<Int, NoteEntity>

    @Query("delete from NoteEntity where id = :id")
    suspend fun deleteNoteById(id: String)

    @Query("DELETE FROM NoteEntity")
    suspend fun clearNotes()
}