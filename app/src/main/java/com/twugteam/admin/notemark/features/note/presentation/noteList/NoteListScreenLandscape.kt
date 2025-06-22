package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteGraphSharedScreen
import com.twugteam.admin.notemark.features.note.presentation.model.NoteUi

@Composable
fun NoteListScreenLandscape(
    modifier: Modifier = Modifier,
    topBarModifier: Modifier = Modifier,
    username: String,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    noteList: List<NoteUi>
) {
    NoteGraphSharedScreen(
        modifier = modifier,
        topBarModifier = topBarModifier,
        username = username,
        noteMarkListPaddingValues = noteMarkListPaddingValues,
        verticalSpace = verticalSpace,
        horizontalSpace = horizontalSpace,
        staggeredGridCells = staggeredGridCells,
        noteList = noteList
    )
}
