package com.twugteam.admin.notemark.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.twugteam.admin.notemark.app.presentation.MainViewModel
import com.twugteam.admin.notemark.app.presentation.NoteMarkApp
import com.twugteam.admin.notemark.core.data.auth.AuthInfoSerializable
import com.twugteam.admin.notemark.core.data.auth.AuthInfoSerializer
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val Context.authInfoDataStore: DataStore<AuthInfoSerializable?> by dataStore(
    fileName = "auth_info",
    serializer = AuthInfoSerializer
)

val Context.syncDataStore: DataStore<Preferences>
    get() = PreferenceDataStoreFactory.create(
        produceFile = { preferencesDataStoreFile("sync_interval") }
    )

val Context.refreshTokenDataStore: DataStore<Preferences>
    get() = PreferenceDataStoreFactory.create(
        produceFile = { preferencesDataStoreFile("refreshToken") }
    )

val appModule = module {
    single<Context> { androidApplication().applicationContext }

    single<DataStore<AuthInfoSerializable?>>(named("authInfoDataStore")) {
            //get applicationContext from koin
            get<Context>().authInfoDataStore
    }

    single<DataStore<Preferences>>(named("syncDataStore")) {
        get<Context>().syncDataStore
    }

    single<DataStore<Preferences>>(named("refreshTokenDataStore")){
        get<Context>().refreshTokenDataStore
    }
    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }
    viewModelOf(::MainViewModel)
}