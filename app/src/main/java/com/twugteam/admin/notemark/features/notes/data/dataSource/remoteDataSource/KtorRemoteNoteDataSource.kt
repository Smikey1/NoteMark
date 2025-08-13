package com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource

import com.twugteam.admin.notemark.core.constant.ApiEndpoints
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.EmptyResult
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.mapToResult
import com.twugteam.admin.notemark.core.domain.util.toDataErrorNetwork
import com.twugteam.admin.notemark.core.networking.AccessTokenRequest
import com.twugteam.admin.notemark.core.networking.delete
import com.twugteam.admin.notemark.core.networking.get
import com.twugteam.admin.notemark.core.networking.post
import com.twugteam.admin.notemark.core.networking.put
import com.twugteam.admin.notemark.features.notes.data.model.CreateNoteRequest
import com.twugteam.admin.notemark.features.notes.data.model.NoteDto
import com.twugteam.admin.notemark.features.notes.data.model.SyncOperationResponse
import com.twugteam.admin.notemark.features.notes.data.model.toCreateNoteRequest
import com.twugteam.admin.notemark.features.notes.data.model.toNote
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.NoteId
import io.ktor.client.HttpClient
import timber.log.Timber

class KtorRemoteNoteDataSource(
    private val httpClient: HttpClient
) : RemoteNoteDataSource {
    override suspend fun fetchNotesById(id: NoteId): Result<Note, DataError.Network> {
        return httpClient.get<NoteDto>(
            route = ApiEndpoints.NOTES_ENDPOINT,
            queryParams = mapOf(
                "id" to id
            )
        ).mapToResult(
            success = { it.toNote() },
            networkError = { it.toDataErrorNetwork() }
        )
    }

    override suspend fun fetchAllNotes(
        //by default page = -1 size = 0 which means will fetch all notes
        page: Int,
        size: Int
    ): Result<List<Note>, DataError.Network> {
        Timber.Forest.tag("AllNotes").d("page: $page size: $size")
        return httpClient.get<SyncOperationResponse>(
            route = ApiEndpoints.NOTES_ENDPOINT,
            queryParams = mapOf(
                "page" to page,
                "size" to size
            )
        ).mapToResult(
            success = { list -> list.notes.map { it.toNote() } },
            networkError = { it.toDataErrorNetwork() }
        )
    }


    override suspend fun postNote(note: Note): Result<Note, DataError.Network> {
        val postNote = httpClient.post<CreateNoteRequest, NoteDto>(
            route = ApiEndpoints.NOTES_ENDPOINT,
            body = note.toCreateNoteRequest()
        ).mapToResult(
            success = { it.toNote() },
            networkError = { it.toDataErrorNetwork() }
        )
        when (postNote) {
            is Result.Error -> Timber.Forest.tag("MyTag").e("postNote: error ${postNote.error}")
            is Result.Success -> Timber.Forest.tag("MyTag").d("postNote: success ${postNote.data}")
        }
        return postNote
    }

    override suspend fun putNote(note: Note): Result<Note, DataError.Network> {
        val putNote = httpClient.put<CreateNoteRequest, NoteDto>(
            route = ApiEndpoints.NOTES_ENDPOINT,
            body = note.toCreateNoteRequest()
        ).mapToResult(
            success = { it.toNote() },
            networkError = { it.toDataErrorNetwork() }
        )
        when (putNote) {
            is Result.Error -> Timber.Forest.tag("MyTag").e("putNote: error ${putNote.error}")
            is Result.Success -> Timber.Forest.tag("MyTag").d("putNote: success ${note.title}")
        }
        return putNote
    }

    override suspend fun deleteNoteById(id: NoteId): EmptyResult<DataError.Network> {
        return httpClient.delete<Unit>(
            route = ApiEndpoints.NOTES_ENDPOINT + "/$id"
        ).mapToResult(
            success = { },
            networkError = { it.toDataErrorNetwork() }
        )
    }

    override suspend fun logout(refreshToken: String): EmptyResult<DataError.Network> {
        val accessTokenRequest = AccessTokenRequest(refreshToken = refreshToken)
        return httpClient.post<AccessTokenRequest, Unit>(
            route = ApiEndpoints.LOGOUT_ENDPOINT,
            body = accessTokenRequest
        ).mapToResult(
            success = {},
            networkError = { it.toDataErrorNetwork() }
        )
    }
}