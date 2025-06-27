package com.twugteam.admin.notemark.features.notes.presentation.noteList.orientationScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteGraphSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.noteList.viewmodel.NoteListAction
import com.twugteam.admin.notemark.features.notes.presentation.noteList.state.NoteListState

@Composable
fun NoteListScreenMobilePortrait(
    modifier: Modifier = Modifier,
    topBarModifier: Modifier = Modifier,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    state: NoteListState,
    onActions: (NoteListAction) -> Unit,
    windowSize: WindowWidthSizeClass
) {
    NoteGraphSharedScreen(
        modifier = modifier,
        topBarModifier = topBarModifier,
        noteMarkListPaddingValues = noteMarkListPaddingValues,
        verticalSpace = verticalSpace,
        horizontalSpace = horizontalSpace,
        staggeredGridCells = staggeredGridCells,
        state = state,
        onActions = onActions,
        windowSize = windowSize
    )
}





