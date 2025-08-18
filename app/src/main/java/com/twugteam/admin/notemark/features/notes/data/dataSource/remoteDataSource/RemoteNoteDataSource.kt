package com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.NoteId

interface RemoteNoteDataSource {
    suspend fun fetchNotesById(id: NoteId): Result<Note, DataError.Network>
    suspend fun fetchNotesByPageAndSize(page: Int = -1, size: Int = 0): Result<List<Note>, DataError.Network>
    suspend fun fetchNotesTotal(): Result<Int, DataError.Network>
    suspend fun postNote(note: Note): Result<Note, DataError.Network>
    suspend fun putNote(note: Note): Result<Note, DataError.Network>
    suspend fun deleteNoteById(id: NoteId): EmptyResult<DataError.Network>
    suspend fun logout(refreshToken: String): EmptyResult<DataError.Network>
}