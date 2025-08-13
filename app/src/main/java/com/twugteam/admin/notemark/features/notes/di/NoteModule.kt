package com.twugteam.admin.notemark.features.notes.di

import com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource.KtorRemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.OfflineFirstDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.RoomLocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource.RoomLocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.SyncIntervalDataStoreImpl
import com.twugteam.admin.notemark.core.data.syncing.SyncRepositoryImpl
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.syncInterval.SyncIntervalDataStoreDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.syncInterval.SyncIntervalDataStoreDataSourceImpl
import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSource
import com.twugteam.admin.notemark.core.data.syncing.dataSource.SyncDataSourceImpl
import com.twugteam.admin.notemark.features.notes.data.dataSource.localNoteDataSource.LocalNoteDataSource
import com.twugteam.admin.notemark.features.notes.data.dataSource.localSyncDataSource.LocalSyncDataSource
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.data.dataSource.remoteDataSource.RemoteNoteDataSource
import com.twugteam.admin.notemark.features.notes.domain.SyncIntervalDataStore
import com.twugteam.admin.notemark.core.domain.SyncRepository
import com.twugteam.admin.notemark.features.notes.data.dataSource.RemoteNotesFetchRepositoryImpl
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.fetchRemoteDataStore.RemoteNotesFetchDataStore
import com.twugteam.admin.notemark.features.notes.data.dataSource.preferencesDataStore.fetchRemoteDataStore.RemoteNotesFetchDataStoreImpl
import com.twugteam.admin.notemark.features.notes.domain.RemoteNotesFetchRepository
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
    singleOf(::RemoteNotesFetchDataStoreImpl).bind<RemoteNotesFetchDataStore>()
    singleOf(::RemoteNotesFetchRepositoryImpl).bind<RemoteNotesFetchRepository>()

    single<SyncIntervalDataStoreDataSource>{
        SyncIntervalDataStoreDataSourceImpl(
            dataStore = get(named("syncDataStore"))
        )
    }

    single<RemoteNotesFetchDataStore>{
        RemoteNotesFetchDataStoreImpl(
            dataStore = get(named("remoteNotesFetchDataStore"))
        )
    }

    singleOf(::SyncIntervalDataStoreImpl).bind<SyncIntervalDataStore>()

}