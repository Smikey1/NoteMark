package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            Timber.tag("WindowSize").d("Compact")
            NoteListScreenMobilePortrait(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("WindowSize").d("Medium")
            NoteListScreenTablet()

        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            Timber.tag("WindowSize").d("Expanded")
            NoteListScreenLandscape()
        }

        //else
        else -> {
            Timber.tag("WindowSize").d("Else")
            NoteListScreenMobilePortrait(
                modifier = Modifier
                    .statusBarsPadding()
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }

}