package com.twugteam.admin.notemark.features.notes.di

import com.twugteam.admin.notemark.features.notes.data.KtorRemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.OfflineFirstNoteRepositoryImpl
import com.twugteam.admin.notemark.features.notes.data.RoomLocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.worker.CreateRunWorker
import com.twugteam.admin.notemark.features.notes.data.worker.DeleteRunWorker
import com.twugteam.admin.notemark.features.notes.data.worker.FetchNoteWorker
import com.twugteam.admin.notemark.features.notes.domain.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val noteModule = module {
    singleOf(::OfflineFirstNoteRepositoryImpl).bind<NoteRepository>()
    singleOf(::KtorRemoteNoteDataSource).bind<RemoteNoteDataSource>()
    singleOf(::RoomLocalNoteDataSource).bind<LocalNoteDataSource>()

    // For Workers
    workerOf(::FetchNoteWorker)
    workerOf(::DeleteRunWorker)
    workerOf(::CreateRunWorker)
}