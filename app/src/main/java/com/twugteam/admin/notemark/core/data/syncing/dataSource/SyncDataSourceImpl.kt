package com.twugteam.admin.notemark.core.data.syncing.dataSource

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.twugteam.admin.notemark.features.notes.constant.Constants.MANUAL_SYNC_WORK_NAME
import com.twugteam.admin.notemark.features.notes.constant.Constants.MANUAL_WORK_TAG
import com.twugteam.admin.notemark.features.notes.constant.Constants.SYNC_INTERVAL_WORK_NAME
import com.twugteam.admin.notemark.features.notes.constant.Constants.SYNC_INTERVAL_WORK_TAG
import com.twugteam.admin.notemark.features.notes.data.workManager.SyncingWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SyncDataSourceImpl(
    context: Context
) : SyncDataSource {
    private val workManager = WorkManager.getInstance(context)

    private val manualWorkName = MANUAL_SYNC_WORK_NAME
    private val syncIntervalWorkName = SYNC_INTERVAL_WORK_NAME

    private val manualWorkTag = MANUAL_WORK_TAG
    private val syncingWorkTag = SYNC_INTERVAL_WORK_TAG

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override suspend fun manualSync(){
        Timber.tag("SyncingWorker").d("manualSync started")
        val syncingRequest =
            OneTimeWorkRequestBuilder<SyncingWorker>().addTag(manualWorkTag).build()

        //this ensure ExistingWorkPolicy.REPLACE if manualSync() clicked multiple times to replace the old syncing running
        //with this one it's like cancelling the old one and starting new one
        workManager.enqueueUniqueWork(
            manualWorkName,
            ExistingWorkPolicy.REPLACE,
            syncingRequest
        )
    }

    override suspend fun syncWithInterval(interval: Long?, timeUnit: TimeUnit?) {
        Timber.tag("SyncingWorker").d("syncWithInterval started")
        if (interval == null || timeUnit == null) {
            //.await() to wait the operation to finish cancelling to check for result Success/Failure
            val operation = workManager.cancelUniqueWork(SYNC_INTERVAL_WORK_NAME).await()

            Timber.tag("SyncingWorker").d("syncWithInterval cancellation operation: $operation")
            return
        }
        Timber.tag("SyncingWorker")
            .d("periodicWorkRequest started interval: $interval and timeUnit: $timeUnit")

        //if constraints isn't achieved(no internet) and we call manualSync() nothing will happen
        //but when internet is restored/turned on the workerManager will start working alone
        //setInitialDelay is used to avoid syncing immediately when enqueue it
        //like that it will sync after the given interval without syncing immediately after being called
        val periodicWorkRequest = PeriodicWorkRequestBuilder<SyncingWorker>(interval, timeUnit)
            .setConstraints(constraints)
            .setInitialDelay(interval, timeUnit)
            .addTag(syncingWorkTag)
            .build()

        //ExistingPeriodicWorkPolicy.REPLACE when the sync interval changes cancel the old syncWorker and start new one
        //ExistingPeriodicWorkPolicy.KEEP if i want to keep the old syncWorker running when i try to run new one
        //started with 30minutes after 20 minutes i change interval to 15minutes and sync worker again
        //if i was using ExistingPeriodicWorkPolicy.REPLACE it will cancel the old one(30minutes) and start
        //new one with (15minutes) but if i was using ExistingPeriodicWorkPolicy.KEEP it will keep the old one (30minutes)
        //and ignore the 15minutes(new one)
        workManager.enqueueUniquePeriodicWork(
            syncIntervalWorkName,
            ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest
        )
    }

    //only cancel queue syncing worker but keep manual running
    override suspend fun cancelIntervalWorker(): Boolean {
       return try {
           //this will cancel all work
//           workManager.cancelAllWork()

           //this can be used if we have multiple work working and we want to cancel some by name
//            workManager.cancelUniqueWork(manualWorkName).await()
//            workManager.cancelUniqueWork(syncIntervalWorkName).await()


           //this can be used to cancel some working work by tag
//           workManager.cancelAllWorkByTag(manualWorkTag)
           workManager.cancelAllWorkByTag(syncingWorkTag)

            Timber.tag("SyncingWorker").d("cancelIntervalWorker: success")
            true
        } catch (e: Exception){
            Timber.tag("SyncingWorker").e("cancelIntervalWorker: ${e.localizedMessage}")
            false
        }
    }

}