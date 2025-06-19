package com.twugteam.admin.notemark.features.notes.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.twugteam.admin.notemark.core.database.mappers.toNote
import com.twugteam.admin.notemark.core.database.notes.NotePendingSyncDao
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource

class CreateRunWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val pendingSyncDao: NotePendingSyncDao
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }
        val pendingNoteId = params.inputData.getString(NOTE_ID) ?: return Result.failure()
        val pendingNoteEntity = pendingSyncDao.getCreatedNotePendingSyncEntityById(pendingNoteId)
            ?: return Result.failure()
        val note = pendingNoteEntity.noteEntity.toNote()
        return when (val result = remoteNoteDataSource.postNote(note)){
            is com.twugteam.admin.notemark.core.domain.util.Result.Error -> result.error.toWorkerResult()
            is com.twugteam.admin.notemark.core.domain.util.Result.Success<*> -> {
                pendingSyncDao.deleteCreatedNotePendingSyncEntityById(pendingNoteId)
                Result.success()
            }
        }
    }


    companion object {
        const val NOTE_ID = "NOTE_ID"
    }

}