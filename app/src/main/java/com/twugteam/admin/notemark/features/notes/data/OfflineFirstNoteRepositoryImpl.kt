package com.twugteam.admin.notemark.features.notes.data

import com.twugteam.admin.notemark.core.database.mappers.toNote
import com.twugteam.admin.notemark.core.database.notes.NotePendingSyncDao
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.features.notes.domain.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteId
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstNoteRepositoryImpl(
    private val localNoteDataSource: LocalNoteDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val notePendingSyncDao: NotePendingSyncDao,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage
) : NoteRepository {
    override fun getNoteById(id: NoteId): Flow<Note> {
        return localNoteDataSource.getNotesById(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return localNoteDataSource.getAllNotes()
    }

    override suspend fun fetchNoteById(id: NoteId): EmptyResult<DataError> {
        return when (val result = remoteNoteDataSource.fetchNotesById(id)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localNoteDataSource.upsertNote(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun fetchAllNotes(): EmptyResult<DataError> {
        return when (val result = remoteNoteDataSource.fetchAllNotes()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localNoteDataSource.upsertNotes(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertNote(note: Note): EmptyResult<DataError> {
        val localResult = localNoteDataSource.upsertNote(note)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }
        val noteWithId = note.copy(id = localResult.data)
        val remoteResult = remoteNoteDataSource.postNote(noteWithId)
        return when (remoteResult) {
            is Result.Success -> {
                applicationScope.async {
                    localNoteDataSource.upsertNote(remoteResult.data).asEmptyDataResult()
                }.await()
            }

            is Result.Error -> {
                Result.Success(Unit)
            }
        }
    }

    override suspend fun deleteNoteById(id: NoteId) {
        localNoteDataSource.deleteNoteById(id)

        // For pending Sync
        // For eg, if note created in offline mode then it will be marked as pending sync note which will sync later, but if user suppose to delete `offline created note` in offline mode then
        // i need to remove it from pending sync and return from there, no need to api call
        val isPendingSync = notePendingSyncDao.getCreatedNotePendingSyncEntityById(id) != null
        if (isPendingSync) {
            notePendingSyncDao.deleteCreatedNotePendingSyncEntityById(id)
            return
        }
        val remoteResult = applicationScope.async {
            remoteNoteDataSource.deleteNoteById(id)
        }.await()
    }

    override suspend fun syncPendingNotes() {
        withContext(Dispatchers.IO) {
            val username = sessionStorage.getAuthInto()?.username ?: return@withContext

            val createdOfflineNotes = async {
                notePendingSyncDao.getAllUserCreatedNotePendingSyncEntities(username)
            }

            val deletedOfflineNote = async {
                notePendingSyncDao.getAllUserDeletedNotePendingSyncEntities(username)
            }

            // list of created jobs
            val createdJobs = createdOfflineNotes
                .await()
                .map {
                    launch {
                        val note = it.noteEntity.toNote()
                        when (remoteNoteDataSource.postNote(note)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    notePendingSyncDao.deleteCreatedNotePendingSyncEntityById(it.noteId)
                                }.join()
                            }
                        }
                    }
                }

            // list of deleted jobs
            val deletedJobs = deletedOfflineNote
                .await()
                .map {
                    launch {
                        when (remoteNoteDataSource.deleteNoteById(it.noteId)) {
                            is Result.Success -> {
                                applicationScope.launch {
                                    notePendingSyncDao.deleteDeletedNotePendingSyncEntity(it.noteId)
                                }.join()
                            }

                            is Result.Error -> Unit
                        }
                    }
                }

            createdJobs.forEach { it.join() }
            deletedJobs.forEach { it.join() }
        }
    }

}