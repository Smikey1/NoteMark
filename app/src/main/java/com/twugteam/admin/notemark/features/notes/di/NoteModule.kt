package com.twugteam.admin.notemark.features.notes.di

import com.twugteam.admin.notemark.features.notes.data.dataSource.KtorRemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.OfflineFirstDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.RoomLocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.RoomLocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.SyncIntervalDataStoreImpl
import com.twugteam.admin.notemark.core.data.syncing.SyncRepositoryImpl
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.SyncIntervalDataStoreDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.SyncIntervalDataStoreDataSourceImpl
import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSource
import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSourceImpl
import com.twugteam.admin.notemark.features.notes.domain.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.LocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.domain.RemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.SyncIntervalDataStore
import com.twugteam.admin.notemark.core.domain.SyncRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailViewModel
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListViewModel
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val noteModule = module {

    viewModelOf(::NoteListViewModel)
    viewModelOf(::NoteDetailViewModel)
    viewModelOf(::SettingsViewModel)

    singleOf(::OfflineFirstDataSource).bind<NoteRepository>()
    singleOf(::KtorRemoteNoteDataSource).bind<RemoteNoteDataSource>()
    singleOf(::RoomLocalNoteDataSource).bind<LocalNoteDataSource>()
    singleOf(::RoomLocalSyncDataSource).bind<LocalSyncDataSource>()
    singleOf(::SyncDataSourceImpl).bind<SyncDataSource>()
    singleOf(::SyncRepositoryImpl).bind<SyncRepository>()

    single<SyncIntervalDataStoreDataSource>{
        SyncIntervalDataStoreDataSourceImpl(
            dataStore = get(named("syncDataStore"))
        )
    }
    singleOf(::SyncIntervalDataStoreImpl).bind<SyncIntervalDataStore>()

}