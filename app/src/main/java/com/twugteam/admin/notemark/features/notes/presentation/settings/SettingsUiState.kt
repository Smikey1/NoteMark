package com.twugteam.admin.notemark.features.notes.presentation.settings

import com.twugteam.admin.notemark.core.presentation.ui.UiText

data class SettingsUiState(
    val showSnackBar: Boolean = false,
    val snackBarText: UiText = UiText.DynamicString("")
)