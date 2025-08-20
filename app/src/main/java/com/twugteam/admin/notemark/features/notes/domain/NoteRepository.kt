package com.twugteam.admin.notemark.features.notes.domain

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.NoteId

interface NoteRepository {
    suspend fun getNoteById(id: NoteId): Note
    suspend fun upsertNote(note: Note, isAdd: Boolean): EmptyResult<DataError>
    suspend fun deleteNoteById(id: NoteId)
    suspend fun logOut(): EmptyResult<DataError>
}
