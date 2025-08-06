package com.twugteam.admin.notemark.features.notes.presentation.settings

import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval

sealed interface SettingsActions {
    data object OnBack: SettingsActions
    data object OnLogout: SettingsActions
    data object OnExpand: SettingsActions
    data object ManualSync: SettingsActions
    data class SyncWithInterval(val syncInterval: SyncInterval): SettingsActions
}