package com.twugteam.admin.notemark.features.notes.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository

class FetchNoteWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val noteRepository: NoteRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 3) {
            return Result.failure()
        }
        return when (val result = noteRepository.fetchAllNotes()) {
            is com.twugteam.admin.notemark.core.domain.util.Result.Error -> result.error.toWorkerResult()
            is com.twugteam.admin.notemark.core.domain.util.Result.Success -> Result.success()
        }
    }

}