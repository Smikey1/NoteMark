package com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result

typealias NoteId = String

interface LocalNoteDataSource {
    suspend fun upsertAllNotes(notes: List<Note>)
    suspend fun getNotesById(id: NoteId): Note
    suspend fun upsertNote(note: Note): Result<NoteId, DataError.Local>
    suspend fun deleteNoteById(id: NoteId): Result<Unit, DataError.Local>
    suspend fun clearNotes(): Result<Unit, DataError.Local>
}