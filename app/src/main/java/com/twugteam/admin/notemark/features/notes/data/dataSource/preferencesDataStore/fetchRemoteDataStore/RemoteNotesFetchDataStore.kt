package com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.fetchRemoteDataStore

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result

interface RemoteNotesFetchDataStore {
    suspend fun setShouldFetchRemoteNotes(shouldFetchNotes: Boolean): Result<Unit, DataError.Local>
    suspend fun getShouldFetchRemoteNotes(): Boolean
}