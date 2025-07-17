package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.readerMode

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.NoteDetailContent
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode.NoteDetailViewModeLandscapeTopText

@Composable
fun NoteDetailReaderModeLandscape(
        modifier: Modifier = Modifier,
        state: NoteDetailUiState,
        contentPaddingValues: PaddingValues,
        dividerPaddingValues: PaddingValues = PaddingValues(),
        topPadding: Dp,
        isReadModeActivate: Boolean = false,
        onScreenTap: () -> Unit = {},
        onActions: (NoteDetailActions) -> Unit,
    ) {
        val interactionSource = remember {
            MutableInteractionSource()
        }

        Row(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onScreenTap,
            )
        ) {
            Card(modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )){
                AnimatedVisibility(
                    visible = isReadModeActivate,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300))
                ) {
                    NoteDetailViewModeLandscapeTopText(
                        modifier = Modifier,
                        onBackClick = {
                            onActions(NoteDetailActions.Close)
                        }
                    )
                }
            }

            NoteDetailContent(
                modifier = Modifier.weight(4f)
                    .padding(top = topPadding),
                contentPaddingValues = contentPaddingValues,
                dividerPaddingValues = dividerPaddingValues,
                state = state,
                onScroll = { isVisible->
                    onActions(NoteDetailActions.SetReadModeActivate(isReadModeActivate = isVisible))
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }