package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.landscape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteActions
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteState
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNoteSharedLandscape
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNoteSharedScreen

@Composable
fun UpsertNoteScreenTabletLandscape(
    modifier: Modifier = Modifier,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
){
    UpsertNoteSharedScreen(
        modifier = modifier,
        topBarContent = null,
        scaffoldContent = {
            UpsertNoteSharedLandscape(
                state = state,
                onActions = onActions
            )
        }
    )
}