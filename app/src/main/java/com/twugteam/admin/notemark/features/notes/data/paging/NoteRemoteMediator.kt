package com.twugteam.admin.notemark.features.notes.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.twugteam.admin.notemark.core.database.NoteDatabase
import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource.RemoteNoteDataSource
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class NoteRemoteMediator(
    private val database: NoteDatabase,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val localNoteDataSource: LocalNoteDataSource,
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
                    Timber.tag("NoteRemoteMediator").d("refresh")
                    currentPage = 0
                    0
                }

                LoadType.PREPEND -> {
                    Timber.tag("NoteRemoteMediator").d("prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    Timber.tag("NoteRemoteMediator").d("append")
                    val lastItem = state.lastItemOrNull()
                    Timber.tag("NoteRemoteMediator").d("lastItem: $lastItem")
                    if (currentPage > totalSize) {
                        Timber.tag("NoteRemoteMediator").d("totalSize is: $totalSize")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    val nextPage = currentPage
                    nextPage
                }
            }

            Timber.tag("NoteRemoteMediator").d("pageToLoad: $pageToLoad")

            val response = remoteNoteDataSource.fetchNotesByPageAndSize(
                page = pageToLoad,
                size = size
            ) as Result.Success

            val notes = response.data

            Timber.tag("NoteRemoteMediator").d("users: ${notes.size}")

            //currentPage increment here because when no internet connection
            //if the user clicks paging.retry() it will increment currentPage without loading data
            //but here only increment when data loaded and saved in local db
            currentPage++

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localNoteDataSource.clearNotes()
                }

                localNoteDataSource.upsertAllNotes(notes = notes)
            }

            val endOfPaginationReached = currentPage >= totalSize || notes.isEmpty()
            Timber.tag("NoteRemoteMediator").d("endOfPaginationReached: $endOfPaginationReached")

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}