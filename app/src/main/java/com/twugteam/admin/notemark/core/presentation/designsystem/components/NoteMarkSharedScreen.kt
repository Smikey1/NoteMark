package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun NoteMarkSharedScreen(
    modifier: Modifier = Modifier,
    portraitModifier: Modifier = Modifier,
    @StringRes
    title: Int,
    @StringRes
    description: Int,
    isPortrait: Boolean = true,
    content: @Composable (Modifier) -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = SurfaceLowest,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        ) {
            if (isPortrait) {
                Column(
                    modifier = portraitModifier
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(description),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    content(Modifier)
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp, start = 60.dp, end = 40.dp, bottom = 46.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(title),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(description),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    content(Modifier.weight(1f).padding(start = 24.dp).verticalScroll(rememberScrollState()))
                }
            }

        }
    }
}