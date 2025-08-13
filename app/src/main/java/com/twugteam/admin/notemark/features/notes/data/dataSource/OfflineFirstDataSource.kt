package com.twugteam.admin.notemark.features.notes.data.dataSource

import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSource
import com.twugteam.admin.notemark.core.database.sync.SyncEntity
import com.twugteam.admin.notemark.core.database.sync.SyncOperation
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.core.presentation.ui.formatToString
import com.twugteam.admin.notemark.features.notes.constant.Constants
import com.twugteam.admin.notemark.features.notes.data.model.toDto
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource.LocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.NoteId
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource.RemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.SyncIntervalDataStore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime

class OfflineFirstDataSource(
    private val localNoteDataSource: LocalNoteDataSource,
    private val localSyncDataSource: LocalSyncDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val syncDataSource: SyncDataSource,
    private val sessionStorage: SessionStorage,
    private val syncIntervalDataStore: SyncIntervalDataStore,
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope
) : NoteRepository {
    override suspend fun getNoteById(id: NoteId): Note {
        return localNoteDataSource.getNotesById(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return localNoteDataSource.getAllNotes()
    }

    override suspend fun upsertNote(note: Note, isAdd: Boolean): EmptyResult<DataError> {
        val localResult = localNoteDataSource.upsertNote(note)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        applicationScope.launch {
            //localNoteDataSource.upsertNote(note) will convert note to NoteEntity and save it
            //localNoteDataSource.upsertNote(note) returns NoteEntity.Id when success
            //we use id = localResult.data because the note is saved in database with random UUID which we give at first like
            //note(id = "") but when being saved it will randomly generate it so we have to return it in result
            val noteWithId = note.copy(id = localResult.data)


            //adding new note (isAdd)
            //updating existing note (!isAdd)
            val remoteDataSource = if (isAdd) {
                remoteNoteDataSource.postNote(noteWithId)
            } else {
                remoteNoteDataSource.putNote(noteWithId)
            }

            when (remoteDataSource) {
                is Result.Error -> {
                    //get userId
                    val userId = sessionStorage.getAuthInfo()?.userId ?: ""

                    //noteWithId.d?
                    //converting to noteDto to serialize the data as (json encode/decode)
                    val noteDto = note.copy(id = noteWithId.id ?: "").toDto()

                    //get sync entity with this noteId if already exist in table
                    val syncEntityExist =
                        localSyncDataSource.getSyncEntityByNoteId(noteWithId.id ?: "")

                    val newOperation = if (isAdd) SyncOperation.ADD else {
                        if (syncEntityExist?.operation == SyncOperation.ADD) {
                            SyncOperation.ADD
                        } else {
                            SyncOperation.UPDATE
                        }
                    }

                    //format to syncEntity
                    val syncEntity = SyncEntity(
                        userId = userId,
                        noteId = noteWithId.id ?: "",
                        operation = newOperation,
                        payload = noteDto,
                        timeStamp = ZonedDateTime.now(ZoneId.of("UTC")).formatToString()
                    )
                    localSyncDataSource.upsertSyncOperation(syncEntity = syncEntity)
                    Timber.tag("MyTag")
                        .e("remoteDataSource: upsertNote error is : ${remoteDataSource.error}")
                }

                is Result.Success -> {
                    Timber.tag("MyTag")
                        .d("remoteDataSource: upsertNote success is : ${remoteDataSource.data.title}")
                    localNoteDataSource.upsertNote(remoteDataSource.data)
                }
            }
        }.join()
        return localResult.asEmptyDataResult()
    }

    override suspend fun deleteNoteById(id: NoteId) {
        val localResult = localNoteDataSource.deleteNoteById(id)
        if (localResult is Result.Error) {
            return
        }
        applicationScope.launch {
            val remoteResult = remoteNoteDataSource.deleteNoteById(id)

            val userId = sessionStorage.getAuthInfo()?.userId ?: ""

            //get sync entity with this noteId if already exist in table
            val syncEntityExist =
                localSyncDataSource.getSyncEntityByNoteId(noteId = id)

            //if it's add operation it means in the server it's not already posted
            //so delete operation wouldn't work on the server cause already not added
            //just remove it from sync entity because add/delete while offline shouldn't be synced with server
            if(syncEntityExist?.operation == SyncOperation.ADD){
                //remove this sync operation from sync entity
                localSyncDataSource.deleteSyncOperation(userId = userId, noteId = id)
                return@launch
            }

            when (remoteResult) {
                is Result.Error -> {
                    Timber.tag("MyTag")
                        .e("remoteDataSource: deleteNoteById error is : ${remoteResult.error}")
                    //get userId

                    //
                    localSyncDataSource.upsertSyncOperation(
                        syncEntity = SyncEntity(
                            userId = userId,
                            noteId = id,
                            operation = SyncOperation.DELETE,
                            payload = null,
                            timeStamp = null
                        )
                    )
                }

                is Result.Success -> {
                    Timber.tag("MyTag")
                        .d("remoteDataSource: deleteNoteById success is")
                }
            }
        }.join()
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
                    Timber.tag("MyTag")
                        .d("logout: success")
                    //clear all local notes so when new user log in local notes should be empty
                    localNoteDataSource.clearNotes()

                    //clear session tokens and username/userId
                    sessionStorage.clearAuthInfo()

                    //clear bearer token after logOut
                    httpClient.authProvider<BearerAuthProvider>()?.clearToken()


                    //reset interval to manual
                    syncIntervalDataStore.saveInterval(
                        interval = null,
                        text = Constants.syncingIntervalList.first().text,
                        timeUnit = null
                    )

                    //reset last sync timeStamp to Never synced
                    syncIntervalDataStore.resetTimeStamp()

                    //reset
                    //cancel all sync work that are currently working/in queue such as interval sync
                    val cancelSyncing = syncDataSource.cancelIntervalWorker()
                    Timber.tag("MyTag").d("logout cancelSyncing: $cancelSyncing")
            }
        }
        //if clearNotes is Error return clearNotes error
        return remoteLogOut.asEmptyDataResult()
    }
}