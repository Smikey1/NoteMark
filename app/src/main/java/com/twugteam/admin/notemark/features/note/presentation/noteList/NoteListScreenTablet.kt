package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteGraphSharedScreen

@Composable
fun NoteListScreenTablet(
    modifier: Modifier = Modifier,
    topBarModifier: Modifier = Modifier,
    username: String,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    gridCells: GridCells,
) {
    NoteGraphSharedScreen(
        modifier = modifier,
        topBarModifier = topBarModifier,
        username = username,
        noteMarkListPaddingValues = noteMarkListPaddingValues,
        verticalSpace = verticalSpace,
        horizontalSpace = horizontalSpace,
        gridCells = gridCells
    )
}
