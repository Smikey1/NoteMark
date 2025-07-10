package com.twugteam.admin.notemark.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.twugteam.admin.notemark.app.presentation.MainViewModel
import com.twugteam.admin.notemark.app.presentation.NoteMarkApp
import com.twugteam.admin.notemark.core.data.auth.AuthInfoSerializable
import com.twugteam.admin.notemark.core.data.auth.AuthInfoSerializer
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val Context.dataStore: DataStore<AuthInfoSerializable?> by dataStore(
    fileName = "notemark_pref",
    serializer = AuthInfoSerializer
)

val appModule = module {
    single<Context> { androidApplication().applicationContext }

    single<DataStore<AuthInfoSerializable?>> {
            //get applicationContext from koin
            get<Context>().dataStore
    }

    single<CoroutineScope> {
        (androidApplication() as NoteMarkApp).applicationScope
    }
    viewModelOf(::MainViewModel)
}