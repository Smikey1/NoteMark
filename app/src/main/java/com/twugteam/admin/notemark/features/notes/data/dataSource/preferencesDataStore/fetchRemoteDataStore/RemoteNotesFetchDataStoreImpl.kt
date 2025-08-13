package com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.fetchRemoteDataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.features.notes.constant.Constants.REMOTE_NOTES_FETCH
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber

class RemoteNotesFetchDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
) : RemoteNotesFetchDataStore {
    val remoteNotesFetch = booleanPreferencesKey(REMOTE_NOTES_FETCH)

    override suspend fun setShouldFetchRemoteNotes(shouldFetchNotes: Boolean): Result<Unit, DataError.Local> {
        return try {
            dataStore.edit { preferences ->
                preferences[remoteNotesFetch] = shouldFetchNotes
            }
            Timber.tag("MyTag").d("setShouldFetchRemoteNotes() success: $shouldFetchNotes")
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.tag("MyTag").e("setShouldFetchRemoteNotes() error: ${e.localizedMessage}")
            Result.Error(DataError.Local.DiskFull)
        }
    }

    override suspend fun getShouldFetchRemoteNotes(): Boolean {
        return try {
            val shouldRemoteNotesFetch = dataStore.data.map { preferences ->
                preferences[remoteNotesFetch] != false
            }.first()
            Timber.tag("MyTag").d("getShouldFetchRemoteNotes() success: $shouldRemoteNotesFetch")
            shouldRemoteNotesFetch
        } catch (e: Exception) {
            Timber.tag("MyTag").e("getShouldFetchRemoteNotes() error: ${e.localizedMessage}")
            true
        }
    }
}