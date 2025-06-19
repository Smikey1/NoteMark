package com.twugteam.admin.notemark.features.notes.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.twugteam.admin.notemark.core.database.notes.NotePendingSyncDao
import com.twugteam.admin.notemark.features.notes.data.worker.CreateRunWorker.Companion.NOTE_ID
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource

class DeleteRunWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val remoteNoteDataSource: RemoteNoteDataSource,
    private val notePendingSyncDao: NotePendingSyncDao
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }
        val noteId = params.inputData.getString(NOTE_ID) ?: return Result.failure()
        return when (val result = remoteNoteDataSource.deleteNoteById(noteId)) {
            is com.twugteam.admin.notemark.core.domain.util.Result.Error -> result.error.toWorkerResult()
            is com.twugteam.admin.notemark.core.domain.util.Result.Success -> {
                notePendingSyncDao.deleteDeletedNotePendingSyncEntity(noteId)
                Result.success()
            }
        }
    }


    companion object {
        const val NOTE_ID = "NOTE_ID"
    }
}