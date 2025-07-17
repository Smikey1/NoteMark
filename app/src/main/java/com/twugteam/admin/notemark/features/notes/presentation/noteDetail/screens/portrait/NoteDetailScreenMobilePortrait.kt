package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.portrait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.AnimatedNoteDetailBottomBar
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddPortraitContent
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode.NoteDetailViewModePortrait

@Composable
fun NoteDetailScreenMobilePortrait(
    modifier: Modifier = Modifier,
    state: NoteDetailUiState,
    onActions: (NoteDetailActions) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedNoteDetailBottomBar(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                onActions = onActions
            )
        }
    ) { innerPadding->
        //can remove this condition since for now it never happens
        if(state.mode.isReader){
            //reader mode force landscape but if user rotate manually in reader mode(landscape mode)
            //show him the landscape of this screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(modifier = Modifier.size(200.dp))
            }

        } else if (state.mode.isEdit) {
            NoteDetailEditOrAddSharedScreen(
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                topBarContent = {
                    NoteDetailEditOrAddMobilePortraitTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        onCloseClick = {
                            onActions(NoteDetailActions.OnCloseIconClick)
                        },
                        onSaveNote = {
                            onActions(NoteDetailActions.OnSaveNoteDetailClick)
                        },
                        saveNoteEnabled = state.noteUi != null && state.noteUi.title.isNotBlank() && state.noteUi.content.isNotBlank(),
                        isLoading = state.isLoading
                    )
                },
                scaffoldContent = { paddingValues ->
                    NoteDetailEditOrAddPortraitContent(
                        state = state,
                        onActions = onActions,
                        paddingValues = paddingValues
                    )
                }
            )
        } else if (state.mode.isView) {
            NoteDetailViewModePortrait(
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                state = state,
                onActions = onActions,
            )
        }
    }
}

@Composable
fun NoteDetailEditOrAddMobilePortraitTopBar(
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