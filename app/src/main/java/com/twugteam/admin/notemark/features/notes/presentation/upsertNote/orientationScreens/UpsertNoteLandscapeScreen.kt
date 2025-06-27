package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.orientationScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceVar
import com.twugteam.admin.notemark.core.presentation.designsystem.Primary
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkDialog
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.viewmodel.UpsertNoteActions
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.state.UpsertNoteState
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteTextField

@Composable
fun UpsertNoteLandscapeScreen(
    modifier: Modifier = Modifier,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
) {
    UpsertNoteSharedScreen(
        modifier = modifier,
        topBarContent = {
        },
        scaffoldContent = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().verticalScroll(state = rememberScrollState())
                    .imePadding()
            ) {
                UpsertNoteLandscape(
                    modifier = Modifier.fillMaxWidth(),
                    onCloseClick = {
                        onActions(UpsertNoteActions.OnCloseIconClick)
                    },
                    onSaveNote = {
                        onActions(UpsertNoteActions.OnSaveNoteClick)
                    },
                    saveNoteEnabled = state.noteUi != null && state.noteUi.title.isNotBlank() && state.noteUi.content.isNotBlank(),
                    isLoading = state.isLoading,
                ) { contentModifier ->
                    UpsertNoteLandscapeContent(
                        modifier = contentModifier,
                        titleValue = state.noteUi?.title ?: "",
                        onTitleValueChange = { newTitle ->
                            onActions(UpsertNoteActions.UpdateNoteUiTitle(noteTitle = newTitle))
                        },
                        contentValue = state.noteUi?.content ?: "",
                        onContentValueChange = { newContent ->
                            onActions(UpsertNoteActions.UpdateNoteUiContent(noteContent = newContent))
                        }
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
    )
}

@Composable
fun UpsertNoteLandscapeContent(
    modifier: Modifier,
    titleValue: String,
    onTitleValueChange: (String) -> Unit,
    contentValue: String,
    onContentValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        UpsertNoteTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = titleValue,
            placeHolderResId = R.string.note_title,
            onValueChange = onTitleValueChange,
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
                .fillMaxWidth(),
            value = contentValue,
            onValueChange = onContentValueChange,
            placeHolderResId = R.string.note_content,
            showIndicator = false,
            textFieldStyle = MaterialTheme.typography.bodyLarge.copy(
                letterSpacing = 0.01f.em,
                color = OnSurfaceVar
            ),
            placeHolderStyle = MaterialTheme.typography.titleSmall.copy(
                letterSpacing = 0.01.em,
                color = OnSurfaceVar
            ),
        )


    }
}

@Composable
fun UpsertNoteLandscape(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onSaveNote: () -> Unit,
    saveNoteEnabled: Boolean,
    isLoading: Boolean,
    content: @Composable (Modifier) -> Unit,
) {
    Row(
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Max),
    ) {
        IconButton(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 30.dp),
            onClick = onCloseClick,
            enabled = !isLoading
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Close,
                contentDescription = "Close",
                tint = OnSurfaceVar,
            )
        }

        content(
            Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        TextButton(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 30.dp),
            onClick = onSaveNote,
            enabled = saveNoteEnabled && !isLoading
        ) {
            Text(
                text = stringResource(R.string.save_note).uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    letterSpacing = 0.01.em,
                    color = Primary,
                ),
            )
        }
    }
}