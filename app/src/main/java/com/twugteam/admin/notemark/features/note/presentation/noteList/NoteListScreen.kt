package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
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
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                gridCells = GridCells.Fixed(2)
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("WindowSize").d("Medium")
            NoteListScreenTablet(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                gridCells = GridCells.Fixed(2)
            )

        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            Timber.tag("WindowSize").d("Expanded")
            NoteListScreenLandscape(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(start = 60.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(start = 60.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                gridCells = GridCells.Fixed(3)
            )
        }

        //else
        else -> {
            Timber.tag("WindowSize").d("Else")
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                gridCells = GridCells.Fixed(2)
            )
        }
    }

}