package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.orientationScreens.UpsertNoteLandscapeScreen
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.orientationScreens.UpsertNoteMobilePortraitScreen
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.orientationScreens.UpsertNoteTabletScreen

@Composable
fun UpsertNoteScreenRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
){

    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            UpsertNoteMobilePortraitScreen(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            UpsertNoteTabletScreen(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            UpsertNoteLandscapeScreen(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }

        else -> {
            UpsertNoteMobilePortraitScreen(
                modifier = modifier,
                state = state,
                onActions = onActions,
            )
        }
    }
}