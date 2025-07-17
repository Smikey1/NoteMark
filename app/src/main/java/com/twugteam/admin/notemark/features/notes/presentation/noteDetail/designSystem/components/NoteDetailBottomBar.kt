package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState

@Composable
fun AnimatedNoteDetailBottomBar(
    modifier: Modifier = Modifier,
    state: NoteDetailUiState,
    onActions: (NoteDetailActions) -> Unit
){
    if(!state.mode.isEdit) {
        AnimatedVisibility(
            //if it's not readerMode visibility should always be true
            visible = if(state.mode.isReader) state.isReadModeActivate else {true},
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300))
        ) {
            NoteDetailBottomBar(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
    }
}

@Composable
fun NoteDetailBottomBar(
    modifier: Modifier = Modifier,
    state: NoteDetailUiState,
    onActions: (NoteDetailActions) -> Unit,
){
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (state.mode.isEdit) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface
                        ),
                    onClick = {
                        onActions(NoteDetailActions.EditMode)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.edit),
                        contentDescription = stringResource(R.string.edit),
                        tint = if (state.mode.isEdit) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (state.mode.isReader) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface
                        ),
                    onClick = {
                        onActions(NoteDetailActions.ReaderMode)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.reader),
                        contentDescription = stringResource(R.string.reader),
                        tint = if (state.mode.isReader) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
    }
}