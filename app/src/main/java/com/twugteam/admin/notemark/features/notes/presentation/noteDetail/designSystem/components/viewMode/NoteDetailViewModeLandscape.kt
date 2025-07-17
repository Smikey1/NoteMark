package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailActions
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.NoteDetailContent

@Composable
fun NoteDetailViewModeLandscape(
    modifier: Modifier = Modifier,
    state: NoteDetailUiState,
    contentPaddingValues: PaddingValues,
    dividerPaddingValues: PaddingValues = PaddingValues(),
    topPadding: Dp,
    onActions: (NoteDetailActions) -> Unit,
) {

    Row(
        modifier = modifier
    ) {
        NoteDetailViewModeLandscapeTopText(
            modifier = Modifier.weight(1f),
            onBackClick = {
                onActions(NoteDetailActions.Close)
            }
        )

        NoteDetailContent(
            modifier = Modifier
                .weight(4f)
                .padding(top = topPadding),
            contentPaddingValues = contentPaddingValues,
            dividerPaddingValues = dividerPaddingValues,
            state = state,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NoteDetailViewModeLandscapeTopText(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.clickable (
            indication = null,
            interactionSource = interactionSource,
            onClick = onBackClick
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onBackClick
        ) {
            Icon(
                imageVector = NoteMarkIcons.Back,
                contentDescription = stringResource(R.string.back)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier,
            text = stringResource(R.string.all_notes).uppercase(),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.01.em,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}