package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.landscape.UpsertNoteScreenMobileLandscape
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.landscape.UpsertNoteScreenTabletLandscape
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.portrait.UpsertNoteScreenMobilePortrait
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.portrait.UpsertNoteScreenTabletPortrait

@Composable
fun UpsertNoteScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
) {
    val deviceConfiguration =
        DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            UpsertNoteScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            UpsertNoteScreenMobileLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            UpsertNoteScreenTabletPortrait(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            UpsertNoteScreenTabletLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.DESKTOP -> {
            TODO()
        }
    }
}