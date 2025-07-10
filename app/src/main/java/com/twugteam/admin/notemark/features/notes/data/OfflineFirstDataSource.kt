package com.twugteam.admin.notemark.features.notes.data

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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

class OfflineFirstDataSource(
    private val localNoteDataSource: LocalNoteDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
) : NoteRepository {
    override suspend fun getNoteById(id: NoteId): Note {
        return localNoteDataSource.getNotesById(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return localNoteDataSource.getAllNotes()
    }

    override suspend fun fetchNoteById(id: NoteId): EmptyResult<DataError> {
        val result = remoteNoteDataSource.fetchNotesById(id)
        return when (result) {
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

    override suspend fun upsertNote(note: Note, isEditing: Boolean): EmptyResult<DataError> {
        val localResult = localNoteDataSource.upsertNote(note)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        //localNoteDataSource.upsertNote(note) will convert note to NoteEntity and save it
        //localNoteDataSource.upsertNote(note) returns NoteEntity.Id when success
        val noteWithId = note.copy(id = localResult.data)

        val remoteDataSource = if (!isEditing) {
            remoteNoteDataSource.postNote(noteWithId)
        } else {
            remoteNoteDataSource.putNote(noteWithId)
        }

        when (remoteDataSource) {
            is Result.Error -> Timber.tag("MyTag")
                .e("remoteDataSource error is : ${remoteDataSource.error}")

            is Result.Success -> {
                Timber.tag("MyTag").d("remoteDataSource success is : ${remoteDataSource.data}")
                localNoteDataSource.upsertNote(remoteDataSource.data)
            }
        }

        return localResult.asEmptyDataResult()
    }

    override suspend fun deleteNoteById(id: NoteId) {
        localNoteDataSource.deleteNoteById(id)
        val remoteResult = applicationScope.async {
            remoteNoteDataSource.deleteNoteById(id)
        }.await()
        Timber.tag("MyTag").d("delete note: ${remoteResult.asEmptyDataResult()}")
    }

    override suspend fun logOut(): EmptyResult<DataError> {
        val refreshToken = sessionStorage.getAuthInfo()?.refreshToken

        //logout from server
        val remoteLogOut = remoteNoteDataSource.logout(refreshToken = refreshToken ?: "")
        when (remoteLogOut) {
            is Result.Error -> {
                Timber.tag("MyTag")
                    .e("logout: ${remoteLogOut.error}")
            }

            is Result.Success -> {
                applicationScope.launch {
                    val clearNotes = localNoteDataSource.clearNotes()

                    //clear session tokens and username
                    clearAuthInfo()

                    when (clearNotes) {
                        is Result.Error<*> -> Timber.tag("MyTag")
                            .e("clearNotes: ${clearNotes.error}")
                        is Result.Success<*> -> Timber.tag("MyTag").d("clearNotes: Success")
                    }
                }
            }
        }
        return remoteLogOut.asEmptyDataResult()
    }


    //clearing refreshToken only and username
    private suspend fun clearAuthInfo() {
        try {
            sessionStorage.setAuthInfo(null)
            Timber.tag("MyTag").d("clearAuthInfo: success")
        } catch (e: Exception) {
            Timber.tag("MyTag").e("clearAuthInfo: ${e.localizedMessage}")
        }
    }
}