package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons

@Composable
fun NoteDetailEditOrAddLandscape(
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
            enabled = !isLoading,
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
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