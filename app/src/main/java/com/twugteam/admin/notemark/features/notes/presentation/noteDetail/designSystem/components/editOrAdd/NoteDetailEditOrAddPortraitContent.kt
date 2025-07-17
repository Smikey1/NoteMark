package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd

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
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.NoteDetailTextField

@Composable
fun NoteDetailEditOrAddPortraitContent(
    state: NoteDetailUiState,
    onActions: (NoteDetailActions) -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            NoteDetailTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.noteUi?.title,
                placeHolderResId = R.string.note_title,
                onValueChange = { newTitle ->
                    onActions(NoteDetailActions.UpdateNoteDetailUiTitle(noteTitle = newTitle))
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

            NoteDetailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                value = state.noteUi?.content,
                onValueChange = { newContent ->
                    onActions(NoteDetailActions.UpdateNoteDetailUiContent(noteContent = newContent))
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
                    onActions(NoteDetailActions.SaveNoteDetail(noteUi = state.noteUi!!))
                } else {
                    onActions(NoteDetailActions.Close)
                }
            },
            onDismissClick = {
                onActions(NoteDetailActions.OnDialogDismiss)
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