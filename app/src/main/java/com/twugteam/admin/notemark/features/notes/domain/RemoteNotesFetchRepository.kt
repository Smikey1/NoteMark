package com.twugteam.admin.notemark.features.notes.domain

import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result

interface RemoteNotesFetchRepository {
    suspend fun setShouldFetchRemoteNotes(shouldFetchNotes: Boolean): Result<Unit, DataError.Local>
    suspend fun getShouldFetchRemoteNotes(): Boolean
}