package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkDialog
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteActions
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteState

@Composable
fun UpsertNotePortraitContent(
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.Start
        ) {
            UpsertNoteTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.noteUi?.title,
                placeHolderResId = R.string.note_title,
                onValueChange = { newTitle ->
                    onActions(UpsertNoteActions.UpdateNoteUiTitle(noteTitle = newTitle))
                },
                showIndicator = true,
                textFieldStyle = MaterialTheme.typography.titleMedium.copy(
                    letterSpacing = 0.01.em,
                    fontWeight = FontWeight.Bold
                ),
                placeHolderStyle = MaterialTheme.typography.titleSmall.copy(
                    letterSpacing = 0.01.em,
                    fontWeight = FontWeight.Bold,
                ),
                gainFocus = true
            )

            UpsertNoteTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                value = state.noteUi?.content,
                onValueChange = { newContent ->
                    onActions(UpsertNoteActions.UpdateNoteUiContent(noteContent = newContent))
                },
                placeHolderResId = R.string.note_content,
                showIndicator = false,
                textFieldStyle = MaterialTheme.typography.bodyLarge.copy(
                    letterSpacing = 0.01f.em,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                placeHolderStyle = MaterialTheme.typography.titleSmall.copy(
                    letterSpacing = 0.01.em,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
        }

        NoteMarkDialog(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            showDialog = state.showDialog,
            titleResId = state.titleResId,
            isLoading = state.isLoading,
            bodyResId = state.bodyResId,
            confirmButtonId = state.confirmButtonId,
            dismissButtonId = state.dismissButtonId,
            onConfirmClick = {
                if (state.isSaveNote) {
                    onActions(UpsertNoteActions.SaveNote(noteUi = state.noteUi!!))
                } else {
                    onActions(UpsertNoteActions.Close)
                }
            },
            onDismissClick = {
                onActions(UpsertNoteActions.OnDialogDismiss)
            },
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}