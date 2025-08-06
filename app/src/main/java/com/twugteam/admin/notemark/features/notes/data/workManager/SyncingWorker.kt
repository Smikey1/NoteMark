package com.twugteam.admin.notemark.features.notes.data.workManager

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.database.sync.SyncOperation
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.features.notes.data.model.toNote
import com.twugteam.admin.notemark.features.notes.data.workManager.WorkUtils.makeStatusNotification
import com.twugteam.admin.notemark.features.notes.domain.LocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SyncingWorker(
    private val context: Context,
    private val localSyncDataSource: LocalSyncDataSource,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val sessionStorage: SessionStorage,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private var message: String = context.getString(R.string.sync_in_progress)
    private var result: com.twugteam.admin.notemark.core.domain.util.Result<Any, DataError> =
        com.twugteam.admin.notemark.core.domain.util.Result.Success(200)

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        makeStatusNotification(
            message = message,
            context = context
        )

        return withContext(Dispatchers.IO) {
            return@withContext try {
                //get userId saved in dataStore
                val userId = sessionStorage.getAuthInfo()?.userId ?: ""

                //get all sync operations related to this userId
                val syncOperations =
                    localSyncDataSource.getAllSyncOperationsByUserId(userId = userId)

                syncOperations?.let { syncOperation ->
                    syncOperations.forEach { sync ->
                        result = when (sync.operation) {
                            SyncOperation.ADD -> {
                                remoteNoteDataSource.postNote(note = sync.payload!!.toNote())
                            }

                            SyncOperation.UPDATE -> {
                                remoteNoteDataSource.putNote(note = sync.payload!!.toNote())
                            }

                            SyncOperation.DELETE -> {
                                remoteNoteDataSource.deleteNoteById(id = sync.noteId)
                            }
                        }

                        Timber.tag("SyncingWorker").d("${sync.operation} result: $result")

                        if (result is com.twugteam.admin.notemark.core.domain.util.Result.Success) {
                            localSyncDataSource.deleteSyncOperation(
                                userId = userId,
                                noteId = sync.noteId
                            )
                            message = context.getString(R.string.sync_completed_successfully)
                        } else {
                            val error =
                                result.asEmptyDataResult() as com.twugteam.admin.notemark.core.domain.util.Result.Error
                            message = context.getString(
                                R.string.sync_failed, error.error.toString()
                            )
                            Timber.tag("SyncingWorker").d("error: ${error.error}")

                            makeStatusNotification(message = message, context = context)
                            return@withContext Result.failure()
                        }
                    }
                }

                //if interval or timeUnit was null (Manual only) it will not enter sync operations
                //if it did not enter sync operations show successfully completed
                //if it entered sync operations and result was Success just copy the same message as sync operation result is Success
                //but if entered sync operations and result was error message will enter the else and take message same as message in error
                message = context.getString(R.string.sync_completed_successfully)
                makeStatusNotification(
                    message = message,
                    context = context
                )

                Result.success()
            } catch (e: Exception) {
                Timber.tag("SyncingWorker").e("failed to sync: ${e.localizedMessage}")
                makeStatusNotification(
                    message = context.getString(
                        R.string.sync_failed,
                        e.localizedMessage
                    ), context = context
                )
                Result.failure()
            }
        }
    }
}