package com.twugteam.admin.notemark.features.notes.data

import android.database.sqlite.SQLiteFullException
import android.os.Build
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNotesById(id: String): Flow<Note> {
        return noteDao.getNoteById(id).map {
            it.toNote()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map {
                it.toNote()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertNote(note: Note): Result<NoteId, DataError.Local> {
        return try {
            val noteEntity = note.toNoteEntity()
            noteDao.upsertNote(noteEntity)
            Result.Success(noteEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun upsertNotes(notes: List<Note>): Result<List<NoteId>, DataError.Local> {
        return try {
            val noteEntities = notes.map {
                it.toNoteEntity()
            }
            noteDao.upsertNotes(noteEntities)
            Result.Success(noteEntities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteNoteById(id: String) {
        noteDao.deleteNoteById(id)
    }

}