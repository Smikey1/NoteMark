package com.twugteam.admin.notemark.core.data.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.twugteam.admin.notemark.features.notes.constant.Constants.BROADCAST_RECEIVER_TAG
import com.twugteam.admin.notemark.features.notes.constant.Constants.BROADCAST_RECEIVER_WORK_NAME
import com.twugteam.admin.notemark.features.notes.data.workManager.SyncingWorker
import timber.log.Timber

class MyBroadcastReceiver() : BroadcastReceiver() {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val broadcastReceiverTag = BROADCAST_RECEIVER_TAG

    private val broadcastReceiverWorkName = BROADCAST_RECEIVER_WORK_NAME
    override fun onReceive(context: Context, intent: Intent?) {
        val workManager = WorkManager.getInstance(context)
        Timber.tag("BroadcastReceiverInGame").d("onReceive started: action: ${intent?.action}")
        if (intent?.action == ACTION_BOOT_COMPLETED) {
            Timber.tag("BroadcastReceiverInGame").d("action == Action_Boot_Completed")
            Timber.tag("BroadcastReceiverInGame").d("manualSync started")
            val syncingRequest =
                OneTimeWorkRequestBuilder<SyncingWorker>().setConstraints(constraints)
                    .addTag(broadcastReceiverTag).build()
            //if constraints isn't achieved(no internet) and we call manualSync() nothing will happen
            //but when internet is restored/turned on the workerManager will start working alone
            //this ensure if manualSync() clicked multiple times to keep the old syncing running
            workManager.enqueueUniqueWork(
                broadcastReceiverWorkName,
                ExistingWorkPolicy.REPLACE,
                syncingRequest
            )
        }
    }
}