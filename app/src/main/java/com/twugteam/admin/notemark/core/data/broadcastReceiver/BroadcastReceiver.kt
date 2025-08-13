package com.twugteam.admin.notemark.core.data.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSource
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class MyBroadcastReceiver() : BroadcastReceiver(), KoinComponent {
    private val applicationScope: CoroutineScope by inject()
    private val sessionStorage: SessionStorage by inject()
    private val syncDataSource: SyncDataSource by inject()

    override fun onReceive(context: Context, intent: Intent?) {
        Timber.tag("BroadcastReceiverInApp").d("onReceive started: action: ${intent?.action}")
        if (intent?.action == ACTION_BOOT_COMPLETED) {
          applicationScope.launch {
              val userLoggedIn = sessionStorage.getAuthInfo() != null
              Timber.tag("BroadcastReceiverInApp").d("userLoggedIn: $userLoggedIn")
              if (userLoggedIn) {
                  syncDataSource.manualSync()
              }
          }
        }
    }
}