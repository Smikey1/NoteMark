package com.twugteam.admin.notemark.features.notes.presentation.noteList.screens.landscape

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListActions
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteList.designSystem.components.NoteListSharedScreen

@Composable
fun NoteListScreenTabletLandscape(
    modifier: Modifier = Modifier,
    topBarPaddingValues: PaddingValues,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    state: NoteListUiState,
    onActions: (NoteListActions) -> Unit,
    windowSizeClass: WindowSizeClass
) {
    NoteListSharedScreen(
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