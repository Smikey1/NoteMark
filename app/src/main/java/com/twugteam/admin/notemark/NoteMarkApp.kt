package com.twugteam.admin.notemark

import android.app.Application
import com.twugteam.admin.notemark.core.di.coreModule
import com.twugteam.admin.notemark.features.auth.di.authModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NoteMarkApp : Application() {
    val applicationScope: CoroutineScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(tree = Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@NoteMarkApp)
            modules(
                appModule,
                authModule,
                coreModule
            )
        }
    }
}