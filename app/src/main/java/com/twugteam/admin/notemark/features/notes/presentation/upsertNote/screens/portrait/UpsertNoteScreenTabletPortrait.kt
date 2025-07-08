package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.portrait

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteActions
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteState
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNotePortraitContent
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNoteSharedScreen

@Composable
fun UpsertNoteScreenTabletPortrait(
    modifier: Modifier = Modifier,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
) {
    UpsertNoteSharedScreen(
        modifier = modifier,
        topBarContent = {
            UpsertNoteMobilePortraitTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onCloseClick = {
                    onActions(UpsertNoteActions.OnCloseIconClick)
                },
                onSaveNote = {
                    onActions(UpsertNoteActions.OnSaveNoteClick)
                },
                saveNoteEnabled = state.noteUi != null && state.noteUi.title.isNotBlank() && state.noteUi.content.isNotBlank(),
                isLoading = state.isLoading
            )
        },
        scaffoldContent = { paddingValues ->
            UpsertNotePortraitContent(
                state = state,
                onActions = onActions,
                paddingValues = paddingValues
            )
        }
    )
}
