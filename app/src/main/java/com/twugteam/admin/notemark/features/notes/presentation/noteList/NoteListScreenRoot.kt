package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.notes.presentation.noteList.screens.landscape.NoteListScreenLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteList.screens.landscape.NoteListScreenTabletLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteList.screens.portrait.NoteListScreenMobilePortrait
import com.twugteam.admin.notemark.features.notes.presentation.noteList.screens.portrait.NoteListScreenTabletPortrait

@Composable
fun NoteListScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: NoteListUiState,
    onActions: (NoteListActions) -> Unit,
) {
    val deviceConfiguration =
        DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            NoteListScreenLandscape(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(
                    start = 60.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
                noteMarkListPaddingValues = PaddingValues(
                    start = 60.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(3),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            NoteListScreenTabletPortrait(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                noteMarkListPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            NoteListScreenTabletLandscape(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(
                    start = 24.dp,
                    end = 24.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
                noteMarkListPaddingValues = PaddingValues(
                    start = 24.dp,
                    top = 16.dp,
                    end = 24.dp,
                    bottom = 16.dp
                ),
                verticalSpace = 24.dp,
                horizontalSpace = 24.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(3),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        DeviceConfiguration.DESKTOP -> {
        }
    }
}
