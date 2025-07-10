package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.screens.portrait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteActions
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteUiState
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNotePortraitContent
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components.UpsertNoteSharedScreen

@Composable
fun UpsertNoteScreenMobilePortrait(
    modifier: Modifier = Modifier,
    state: UpsertNoteUiState,
    onActions: (UpsertNoteActions) -> Unit,
) {
    UpsertNoteSharedScreen(
        modifier = modifier,
        topBarContent = {
            UpsertNoteMobilePortraitTopBar(
                modifier = Modifier.fillMaxWidth(),
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

@Composable
fun UpsertNoteMobilePortraitTopBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onSaveNote: () -> Unit,
    saveNoteEnabled: Boolean,
    isLoading: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onCloseClick,
            enabled = !isLoading
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        TextButton(
            onClick = onSaveNote,
            enabled = saveNoteEnabled && !isLoading,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent,
                disabledContentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = stringResource(R.string.save_note).uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    letterSpacing = 0.01.em,
                    color = LocalContentColor.current,
                ),
            )
        }
    }
}