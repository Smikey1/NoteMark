package com.twugteam.admin.notemark.features.notes.presentation.settings

import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval

sealed interface SettingsActions {
    data object OnBack: SettingsActions
    data object OnLogoutClick: SettingsActions
    data object OnExpand: SettingsActions
    data object ManualSync: SettingsActions
    data class SyncWithInterval(val syncInterval: SyncInterval): SettingsActions
    data class OnLogOutConfirm(val withSyncing: Boolean): SettingsActions
    data object OnDismissRequest: SettingsActions
}