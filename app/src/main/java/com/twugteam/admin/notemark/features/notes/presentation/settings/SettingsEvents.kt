package com.twugteam.admin.notemark.features.notes.presentation.settings

sealed interface SettingsEvents {
    data object OnBack: SettingsEvents
    data object OnLogout: SettingsEvents
}