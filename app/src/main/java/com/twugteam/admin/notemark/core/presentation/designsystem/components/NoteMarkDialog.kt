package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteMarkDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    @StringRes titleResId: Int,
    isLoading: Boolean,
    @StringRes bodyResId: Int,
    @StringRes confirmButtonId: Int,
    @StringRes dismissButtonId: Int,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    ) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text(
                    modifier = Modifier,
                    text = stringResource(titleResId),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            },
            text = {
                if (!isLoading) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(bodyResId),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                } else {
                    Box(modifier = Modifier.fillMaxWidth()){
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp).align(Alignment.Center)
                    )
                    }
                }
            },
            //user not allowed to dismiss dialog when clicking outside of dialog only if clicking on cancel
            onDismissRequest = { },
            modifier = modifier,
            confirmButton = {
                NoteMarkActionButton(
                    text = stringResource(confirmButtonId),
                    isLoading = false,
                    modifier = Modifier,
                    enabled = !isLoading,
                    onClick = onConfirmClick
                )
            },
            dismissButton = {
                NoteMarkActionButton(
                    text = stringResource(dismissButtonId),
                    isLoading = false,
                    modifier = Modifier,
                    enabled = !isLoading,
                    onClick = onDismissClick
                )
            }
        )
    }
}