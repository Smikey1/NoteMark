package com.twugteam.admin.notemark.features.notes.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.twugteam.admin.notemark.core.database.NoteDatabase
import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import com.twugteam.admin.notemark.core.database.sync.SyncOperation
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource.LocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource.RemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.model.toNote
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class NoteRemoteMediator(
    private val database: NoteDatabase,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val localNoteDataSource: LocalNoteDataSource,
    private val sessionStorage: SessionStorage,
    private val localSyncDataSource: LocalSyncDataSource,
) : RemoteMediator<Int, NoteEntity>() {
    val size = 20
    var currentPage = 0
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NoteEntity>
    ): MediatorResult {
        return try {
            //get total notes in server
            val totalSize = (remoteNoteDataSource.fetchNotesTotal() as Result.Success).data
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> {
                    Timber.tag("RemoteMediatorNote").d("refresh")
                    currentPage = 0
                    0
                }

                LoadType.PREPEND -> {
                    Timber.tag("RemoteMediatorNote").d("prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    Timber.tag("RemoteMediatorNote").d("append")
                    val lastItem = state.lastItemOrNull()
                    Timber.tag("RemoteMediatorNote").d("lastItem: $lastItem")
                    if (currentPage > totalSize) {
                        Timber.tag("RemoteMediatorNote").d("totalSize is: $totalSize")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    val nextPage = currentPage
                    nextPage
                }
            }

            Timber.tag("RemoteMediatorNote").d("pageToLoad: $pageToLoad")

            val response = remoteNoteDataSource.fetchNotesByPageAndSize(
                page = pageToLoad,
                size = size
            ) as Result.Success

            val notes = response.data

            Timber.tag("RemoteMediatorNote").d("users: ${notes.size}")

            //currentPage increment here because when no internet connection
            //if the user clicks paging.retry() it will increment currentPage without loading data
            //but here only increment when data loaded and saved in local db
            currentPage++

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //clear local notes to save new ones
                    localNoteDataSource.clearNotes()
                }

                //upsert all notes
                localNoteDataSource.upsertAllNotes(notes = notes)

                    //get userId saved in dataStore
                    val userId = sessionStorage.getAuthInfo()?.userId ?: ""

                    //get all sync operations related to this userId
                    val syncOperations =
                        localSyncDataSource.getAllSyncOperationsByUserId(userId = userId)

                    val isSynced = syncOperations.isNullOrEmpty()

                    Timber.tag("RemoteMediatorNote").d("isSynced: $isSynced")


                    if (!isSynced) {
                        Timber.tag("RemoteMediatorNote").d("start saving unSynced data")
                        //keep data in track with sync operations that are unSynced
                            syncOperations.forEach { sync ->
                                when (sync.operation) {
                                    SyncOperation.DELETE -> {
                                        val delete = localNoteDataSource.deleteNoteById(sync.noteId)
                                        when (delete) {
                                            is Result.Error -> Timber.tag("RemoteMediatorNote")
                                                .d("delete operation error")

                                            is Result.Success -> Timber.tag("RemoteMediatorNote")
                                                .d("delete operation success")
                                        }
                                    }
                                    //SyncOperation.UPDATE or SyncOperation.ADD are the same locally
                                    //since both will use upsert function update/insert
                                    else -> {
                                        sync.payload?.let {
                                            val upsert =
                                                localNoteDataSource.upsertNote(sync.payload.toNote())
                                            when (upsert) {
                                                is Result.Error -> Timber.tag("RemoteMediatorNote")
                                                    .d("upsert operation error")

                                                is Result.Success -> Timber.tag("RemoteMediatorNote")
                                                    .d("upsert operation success")
                                            }
                                        }
                                    }
                        }
                    }
                }
            }

            val endOfPaginationReached = currentPage >= totalSize || notes.isEmpty()
            Timber.tag("RemoteMediatorNote").d("endOfPaginationReached: $endOfPaginationReached")

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Timber.tag("RemoteMediatorNote").e("NoteRemoteMediator error: ${e.localizedMessage}")
            MediatorResult.Error(e)
        }
    }
}