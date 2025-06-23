package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpsertNoteSharedScreen(
    modifier: Modifier,
    topBarContent: @Composable () -> Unit,
    scaffoldContent: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            topBarContent()
        },
        containerColor = SurfaceLowest
    ) { innerPadding ->
        scaffoldContent(innerPadding)
    }
}
