package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.screens.landscape

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
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddSharedLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd.NoteDetailEditOrAddSharedScreen
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.readerMode.NoteDetailReaderModeLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode.NoteDetailViewModeLandscape

@Composable
fun NoteDetailScreenMobileLandscape(
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
        if(state.mode.isReader){
            NoteDetailReaderModeLandscape(
                modifier = Modifier.fillMaxWidth().padding(
                    top = 12.dp,
                    bottom = innerPadding.calculateBottomPadding()
                ),
                topPadding = 8.dp,
                state = state,
                contentPaddingValues = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),
                isReadModeActivate = state.isReadModeActivate,
                onScreenTap = {
                    onActions(NoteDetailActions.OnScreenTap)
                },
                dividerPaddingValues = PaddingValues(horizontal = 24.dp),
                onActions = onActions
            )
        } else if (state.mode.isEdit){
        NoteDetailEditOrAddSharedScreen(
            modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            topBarContent = null,
            scaffoldContent = {
                NoteDetailEditOrAddSharedLandscape(
                    state = state,
                    onActions = onActions
                )
            }
        )
    }else if(state.mode.isView){
            NoteDetailViewModeLandscape(
                modifier = Modifier.fillMaxWidth().padding(
                    top = 12.dp,
                    bottom = innerPadding.calculateBottomPadding()
                ),
                topPadding = 8.dp,
                state = state,
                contentPaddingValues = PaddingValues(
                    horizontal = 24.dp,
                    vertical = 20.dp
                ),
                dividerPaddingValues = PaddingValues(horizontal = 24.dp),
                onActions = onActions
            )
        }
    }
}



