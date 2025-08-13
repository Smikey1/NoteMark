package com.twugteam.admin.notemark.features.notes.data

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.fetchRemoteDataStore.RemoteNotesFetchDataStore
import com.twugteam.admin.notemark.features.notes.domain.RemoteNotesFetchRepository

class RemoteNotesFetchRepositoryImpl(
    private val remoteNotesFetchDataStore: RemoteNotesFetchDataStore
): RemoteNotesFetchRepository {
    override suspend fun setShouldFetchRemoteNotes(shouldFetchNotes: Boolean): Result<Unit, DataError.Local> {
        return remoteNotesFetchDataStore.setShouldFetchRemoteNotes(shouldFetchNotes = shouldFetchNotes)
    }

    override suspend fun getShouldFetchRemoteNotes(): Boolean {
        return remoteNotesFetchDataStore.getShouldFetchRemoteNotes()
    }
}