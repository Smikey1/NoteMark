package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.portrait

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.AnimatedNoteDetailBottomBar
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddPortraitContent
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.readerMode.NoteDetailReaderModeLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode.NoteDetailViewModePortrait

@Composable
fun NoteDetailScreenTabletPortrait(
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
    ) { innerPadding ->
        if (state.mode.isReader) {
            NoteDetailReaderModeLandscape(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
                topPadding = 12.dp,
                state = state,
                contentPaddingValues = PaddingValues(
                    vertical = 20.dp
                ),
                isReadModeActivate = state.isReadModeActivate,
                onScreenTap = {
                    onActions(NoteDetailActions.OnScreenTap)
                },
                onActions = onActions
            )
        } else if (state.mode.isEdit) {
            NoteDetailEditOrAddSharedScreen(
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                topBarContent = {
                    NoteDetailEditOrAddMobilePortraitTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
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
                modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                topBarPaddingValues = PaddingValues(horizontal = 8.dp),
                state = state,
                onActions = onActions,
            )
        }
    }
}
