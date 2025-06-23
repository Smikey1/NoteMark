package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceVar
import com.twugteam.admin.notemark.core.presentation.designsystem.Primary

@Composable
fun UpsertNoteMobilePortraitScreen(
    modifier: Modifier = Modifier,
    state: UpsertNoteState,
    onActions: (UpsertNoteActions) -> Unit,
) {
    UpsertNoteSharedScreen(
        modifier = modifier,
        topBarContent = {
            UpsertNoteMobilePortraitTopBar(
                modifier = Modifier.fillMaxWidth(),
                onCloseClick = {
                    onActions(UpsertNoteActions.Close)
                },
                onSaveNote = {
                    onActions(UpsertNoteActions.SaveNote(noteUi = state.noteUi!!))
                },
                saveNoteEnabled = state.noteUi != null && state.noteUi.title.isNotBlank() && state.noteUi.content.isNotBlank()
            )
        },
        scaffoldContent = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
                horizontalAlignment = Alignment.Start
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.noteUi?.title ?: "",
                    onValueChange = { newTitle ->
                        onActions(UpsertNoteActions.UpdateNoteUiTitle(noteTitle = newTitle))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            style = MaterialTheme.typography.titleSmall.copy(
                                letterSpacing = 0.01.em,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        letterSpacing = 0.01.em,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = OnSurfaceVar
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.noteUi?.content ?: "",
                    onValueChange = { newContent ->
                        onActions(UpsertNoteActions.UpdateNoteUiContent(noteContent = newContent))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_content),
                            style = MaterialTheme.typography.titleSmall.copy(
                                letterSpacing = 0.01.em,
                                color = OnSurfaceVar
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        letterSpacing = 0.01f.em,
                        color = OnSurfaceVar
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    )
}

@Composable
fun UpsertNoteMobilePortraitTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onSaveNote: () -> Unit,
    saveNoteEnabled: Boolean,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onCloseClick
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Close,
                contentDescription = "Close",
                tint = OnSurfaceVar,
            )
        }

        TextButton(
            onClick = onSaveNote,
            enabled = saveNoteEnabled
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