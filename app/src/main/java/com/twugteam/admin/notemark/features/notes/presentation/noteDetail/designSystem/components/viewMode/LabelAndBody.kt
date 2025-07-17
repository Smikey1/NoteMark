package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.viewMode

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun LabelAndBody(
    modifier: Modifier = Modifier,
    @StringRes
    label: Int,
    body: String,
    paddingValues: PaddingValues = PaddingValues(),
){
    Column(
        modifier = modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            modifier = Modifier,
            text = stringResource(label),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            modifier = Modifier,
            text = body,
            style = MaterialTheme.typography.titleSmall
        )
    }
}