package com.twugteam.admin.notemark.core.di

import androidx.room.Room
import com.twugteam.admin.notemark.core.data.auth.EncryptedSessionStorage
import com.twugteam.admin.notemark.core.database.notes.NoteDatabase
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.networking.HttpClientFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {

    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    single {
        Room.databaseBuilder(
            androidApplication(),
            klass = NoteDatabase::class.java,
            name = "notes.db"
        ).build()
    }

    single {
        get<NoteDatabase>().noteDao
    }


}