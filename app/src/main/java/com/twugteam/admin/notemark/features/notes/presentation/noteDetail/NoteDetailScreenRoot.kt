package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.landscape.NoteDetailScreenMobileLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.landscape.NoteDetailScreenTabletLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.portrait.NoteDetailScreenMobilePortrait
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.portrait.NoteDetailScreenTabletPortrait
import timber.log.Timber

@Composable
fun NoteDetailScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: NoteDetailUiState,
    onActions: (NoteDetailActions) -> Unit,
) {
    val deviceConfiguration =
        DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Timber.tag("WindowSize").d("MOBILE_PORTRAIT")
            NoteDetailScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Timber.tag("WindowSize").d("MOBILE_LANDSCAPE")
            NoteDetailScreenMobileLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            Timber.tag("WindowSize").d("TABLET_PORTRAIT")
            NoteDetailScreenTabletPortrait(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            Timber.tag("WindowSize").d("TABLET_LANDSCAPE")
            NoteDetailScreenTabletLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        DeviceConfiguration.DESKTOP -> {

        }
    }
}