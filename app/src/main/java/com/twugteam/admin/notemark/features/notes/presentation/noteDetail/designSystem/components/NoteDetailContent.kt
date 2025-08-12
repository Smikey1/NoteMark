package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.ui.formatToViewMode
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailUiState
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode.LabelAndBody

@Composable
fun NoteDetailContent(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    dividerPaddingValues: PaddingValues = PaddingValues(),
    state: NoteDetailUiState,
    labelAndBodySpacing: Dp = 16.dp,
    onScroll: (Boolean) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    if(state.mode.isReader) {
        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.isScrollInProgress }.collect { isScrolling->
                if(isScrolling){
                    onScroll(false)
                }
            }
        }
    }


    Column(
        modifier = modifier.verticalScroll(state = scrollState),
    ) {
        Text(
            modifier = Modifier
                .padding(paddingValues = contentPaddingValues),
            text = state.noteUi?.title ?: stringResource(R.string.note_title),
            style = MaterialTheme.typography.titleMedium.copy(
                letterSpacing = 0.01.em
            )
        )
        HorizontalDivider(modifier = Modifier.padding(dividerPaddingValues).background(color = MaterialTheme.colorScheme.surface))

        Row(
            modifier = Modifier
                .padding(paddingValues = contentPaddingValues),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            LabelAndBody(
                modifier = Modifier.weight(1f),
                label = R.string.date_created,
                body = state.noteUi?.createdAt?.let {
                    formatToViewMode(it).asString()
                } ?: stringResource(R.string.date_created),
            )

            LabelAndBody(
                modifier = Modifier.weight(1f),
                label = R.string.last_edit,
                body = state.noteUi?.lastEditedAt?.let {
                    formatToViewMode(it).asString()
                } ?: stringResource(R.string.last_edit),
                paddingValues = PaddingValues(start = labelAndBodySpacing)
            )
        }

        HorizontalDivider(modifier = Modifier.padding(dividerPaddingValues).background(color = MaterialTheme.colorScheme.surface))

        Text(
            modifier = Modifier
                .padding(paddingValues = contentPaddingValues),
            text = state.noteUi?.content ?: stringResource(R.string.note_content),
            style = MaterialTheme.typography.bodyLarge.copy(
                letterSpacing = 0.01.em,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
        )
    }
}