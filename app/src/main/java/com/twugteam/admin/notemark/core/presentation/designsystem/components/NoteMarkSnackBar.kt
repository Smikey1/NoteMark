package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun NoteMarkSnackBar(
    modifier: Modifier = Modifier,
    data: SnackbarData,
){
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = SurfaceLowest,
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.visuals.message,
                color = LocalContentColor.current
            )

            if (data.visuals.withDismissAction) {
                IconButton(
                    modifier = Modifier,
                    onClick = { data.dismiss() }
                ) {
                    Icon(
                        imageVector = NoteMarkIcons.Close,
                        contentDescription = stringResource(R.string.close),
                        tint = SurfaceLowest
                    )
                }
            }
        }
    }
}