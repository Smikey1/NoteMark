package com.twugteam.admin.notemark.features.notes.domain

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNoteById(id: NoteId): Note
    fun getNotes(): Flow<List<Note>>
    suspend fun fetchNoteById(id: NoteId): EmptyResult<DataError>
    suspend fun fetchAllNotes(): EmptyResult<DataError>
    suspend fun upsertNote(note: Note, isEditing: Boolean): EmptyResult<DataError>
    suspend fun deleteNoteById(id: NoteId)
    suspend fun logOut(): EmptyResult<DataError>
}
