package com.twugteam.admin.notemark.features.notes.domain

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import kotlinx.coroutines.flow.Flow


typealias NoteId = String

interface LocalNoteDataSource {
    fun getNotesById(id: NoteId): Flow<Note>
    fun getAllNotes(): Flow<List<Note>>
    suspend fun upsertNote(note: Note): Result<NoteId, DataError.Local>
    suspend fun upsertNotes(notes: List<Note>): Result<List<NoteId>, DataError.Local>
    suspend fun deleteNoteById(id: NoteId)
}
