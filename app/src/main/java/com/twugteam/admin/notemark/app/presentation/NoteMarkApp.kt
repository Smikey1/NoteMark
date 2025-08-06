package com.twugteam.admin.notemark.app.presentation

import android.app.Application
import com.twugteam.admin.notemark.BuildConfig
import com.twugteam.admin.notemark.app.di.appModule
import com.twugteam.admin.notemark.core.di.coreModule
import com.twugteam.admin.notemark.features.auth.di.authModule
import com.twugteam.admin.notemark.features.notes.di.noteModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class NoteMarkApp : Application() {
    val applicationScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(tree = Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@NoteMarkApp)
            workManagerFactory()
            modules(
                appModule,
                coreModule,
                authModule,
                noteModule,
            )
        }
    }
}