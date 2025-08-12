package com.twugteam.admin.notemark.core.di

import androidx.room.Room
import androidx.work.WorkManager
import com.twugteam.admin.notemark.core.data.auth.EncryptedSessionStorage
import com.twugteam.admin.notemark.core.data.broadcastReceiver.MyBroadcastReceiver
import com.twugteam.admin.notemark.core.data.network.NetworkConnectivityObserver
import com.twugteam.admin.notemark.features.notes.data.workManager.SyncingWorker
import com.twugteam.admin.notemark.core.database.NoteDatabase
import com.twugteam.admin.notemark.core.database.notes.NoteDao
import com.twugteam.admin.notemark.core.database.sync.SyncDao
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.network.ConnectivityObserver
import com.twugteam.admin.notemark.core.networking.HttpClientFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {

    single {
        HttpClientFactory(get()).build()
    }

    single<SessionStorage> {
        EncryptedSessionStorage(
            syncDataStore = get(named("authInfoDataStore")),
            refreshTokenDataStore = get(named("refreshTokenDataStore")),
            context = get()
        )
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            klass = NoteDatabase::class.java,
            name = "notes.db"
        ).build()
    }

    singleOf(::NetworkConnectivityObserver).bind<ConnectivityObserver>()

    single<NoteDao> {
        get<NoteDatabase>().noteDao
    }

    single<SyncDao> {
        get<NoteDatabase>().syncDao
    }

    single{
        WorkManager.getInstance(context = get())
    }

    workerOf(::SyncingWorker)

    singleOf(::MyBroadcastReceiver)


}