package com.twugteam.admin.notemark.features.notes.presentation.noteList.orientationScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.features.notes.presentation.noteList.designSystem.components.NoteGraphSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListActions
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListState

@Composable
fun NoteListScreenLandscape(
    modifier: Modifier = Modifier,
    topBarPaddingValues: PaddingValues,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    state: NoteListState,
    onActions: (NoteListActions) -> Unit,
    windowSizeClass: WindowSizeClass
) {
    NoteGraphSharedScreen(
        modifier = modifier,
        topBarPaddingValues = topBarPaddingValues,
        noteMarkListPaddingValues = noteMarkListPaddingValues,
        verticalSpace = verticalSpace,
        horizontalSpace = horizontalSpace,
        staggeredGridCells = staggeredGridCells,
        state = state,
        onActions = onActions,
        windowSizeClass = windowSizeClass
    )
}
