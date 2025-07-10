package com.twugteam.admin.notemark.features.notes.di

import com.twugteam.admin.notemark.features.notes.data.KtorRemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.OfflineFirstDataSource
import com.twugteam.admin.notemark.features.notes.data.RoomLocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListViewModel
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsViewModel
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val noteModule = module {

    viewModelOf(::NoteListViewModel)
    viewModelOf(::UpsertNoteViewModel)
    viewModelOf(::SettingsViewModel)

    singleOf(::OfflineFirstDataSource).bind<NoteRepository>()
    singleOf(::KtorRemoteNoteDataSource).bind<RemoteNoteDataSource>()
    singleOf(::RoomLocalNoteDataSource).bind<LocalNoteDataSource>()

}