package com.twugteam.admin.notemark.features.notes.presentation.settings

sealed interface SettingsActions {
    data object OnBack: SettingsActions
    data object OnLogout: SettingsActions
}