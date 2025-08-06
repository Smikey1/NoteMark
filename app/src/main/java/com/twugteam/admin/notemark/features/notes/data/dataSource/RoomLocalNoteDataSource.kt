package com.twugteam.admin.notemark.features.notes.data.dataSource

import android.database.sqlite.SQLiteFullException
import com.twugteam.admin.notemark.core.database.mappers.toNote
import com.twugteam.admin.notemark.core.database.mappers.toNoteEntity
import com.twugteam.admin.notemark.core.database.notes.NoteDao
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.domain.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalNoteDataSource(
    private val noteDao: NoteDao
) : LocalNoteDataSource {
    override suspend fun getNotesById(id: NoteId): Note {
        return noteDao.getNoteById(id).toNote()
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map {
                it.toNote()
            }
        }
    }

    override suspend fun upsertNote(note: Note): Result<NoteId, DataError.Local> {
        return try {
            val noteEntity = note.toNoteEntity()
            noteDao.upsertNote(noteEntity)
            Result.Success(noteEntity.id)
        } catch (_: SQLiteFullException) {
            Result.Error(DataError.Local.DiskFull)
        }
    }

    override suspend fun deleteNoteById(id: NoteId): Result<Unit, DataError.Local> {
        return try {
            noteDao.deleteNoteById(id)
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(DataError.Local.Unknown(unknownError = "${e.localizedMessage}"))
        }
    }

    override suspend fun clearNotes(): Result<Unit, DataError.Local> {
        return try {
            noteDao.clearNotes()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(
                error = DataError.Local.Unknown(
                    unknownError = e.localizedMessage?.toString() ?: ""
                )
            )
        }
    }
}