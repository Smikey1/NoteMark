package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

@Composable
fun NoteListScreenMobilePortrait(
    modifier: Modifier = Modifier,
    topBarModifier: Modifier = Modifier,
    username: String,
    noteMarkListPaddingValues: PaddingValues,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    staggeredGridCells: StaggeredGridCells,
    noteList: List<NoteUi>,
    onActions: (NoteListAction) -> Unit,
) {
    NoteGraphSharedScreen(
        modifier = modifier,
        topBarModifier = topBarModifier,
        username = username,
        noteMarkListPaddingValues = noteMarkListPaddingValues,
        verticalSpace = verticalSpace,
        horizontalSpace = horizontalSpace,
        staggeredGridCells = staggeredGridCells,
        noteList = noteList,
        onActions = onActions,
    )
}





