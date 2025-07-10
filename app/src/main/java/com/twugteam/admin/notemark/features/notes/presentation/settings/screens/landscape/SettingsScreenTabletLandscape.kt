package com.twugteam.admin.notemark.features.notes.presentation.settings.screens.landscape

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsActions
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsUiState
import com.twugteam.admin.notemark.features.notes.presentation.settings.designSystem.component.SettingsSharedScreen

@Composable
fun SettingsScreenTabletLandscape(
    modifier: Modifier = Modifier,
    scaffoldPaddingValues: PaddingValues,
    topBarPaddingValues: PaddingValues,
    contentPaddingValues: PaddingValues,
    state: SettingsUiState,
    onActions: (SettingsActions) -> Unit,
){
    SettingsSharedScreen(
        modifier = modifier,
        scaffoldPaddingValues = scaffoldPaddingValues,
        topBarPaddingValues = topBarPaddingValues,
        contentPaddingValues = contentPaddingValues,
        state = state,
        onActions = onActions
    )
}