package com.twugteam.admin.notemark.features.notes.domain

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias NoteId = String

interface LocalNoteDataSource {
    suspend fun getNotesById(id: NoteId): Note
    fun getAllNotes(): Flow<List<Note>>
    suspend fun upsertNote(note: Note): com.twugteam.admin.notemark.core.domain.util.Result<NoteId, DataError.Local>
    suspend fun deleteNoteById(id: NoteId): com.twugteam.admin.notemark.core.domain.util.Result<Unit, DataError.Local>
    suspend fun clearNotes(): Result<Unit, DataError.Local>
}
