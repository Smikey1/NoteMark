package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceOpacity12

@Composable
fun NoteMarkActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    enabled: Boolean = false,
    onClick: () -> Unit,
    ) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            disabledContainerColor = OnSurfaceOpacity12,

        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
 CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.Unspecified,
                ),
            )
        }
    }
}

@Composable
fun NoteMarkOutlineActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    enabled: Boolean = false,
    onClick: () -> Unit,
    ) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,

        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
 CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = LocalContentColor.current,
                ),
            )
        }
    }
}

@Composable
fun NoteMarkNoOutlineActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    enabled: Boolean = false,
    onClick: () -> Unit,
    ) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,

        ),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
 CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun NoteMarkActionButtonPreview() {
    NoteMarkTheme {
        Column {
            NoteMarkActionButton(
                text = "Click Me",
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onClick = {

                }
            )
            Spacer(Modifier.height(10.dp))
            NoteMarkOutlineActionButton(
                text = "Click Me",
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onClick = {

                }
            )
            Spacer(Modifier.height(10.dp))
            NoteMarkNoOutlineActionButton(
                text = "Click Me",
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onClick = {

                }
            )
        }
    }
}