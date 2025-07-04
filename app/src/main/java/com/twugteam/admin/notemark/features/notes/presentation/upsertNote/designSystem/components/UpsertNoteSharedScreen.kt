package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun UpsertNoteSharedScreen(
    modifier: Modifier,
    topBarContent: @Composable (() -> Unit?)?,
    scaffoldContent: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            topBarContent?.let { it() }
        },
        containerColor = SurfaceLowest
    ) { innerPadding ->
        scaffoldContent(innerPadding)
    }
}
