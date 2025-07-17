package com.twugteam.admin.notemark.features.notes.presentation.noteDetail.designSystem.components.editOrAdd

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun NoteDetailEditOrAddSharedScreen(
    modifier: Modifier,
    topBarContent: @Composable (() -> Unit?)?,
    scaffoldContent: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            topBarContent?.let { it() }
        },
        containerColor = SurfaceLowest
    ) { innerPadding ->
        scaffoldContent(innerPadding)
    }
}
