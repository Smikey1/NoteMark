package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteListScreenRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    state: NoteListState,
    onActions: (NoteListAction) -> Unit,
) {

    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                noteList = state.notes,
                onActions = onActions,
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            NoteListScreenTablet(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                noteList = state.notes,
                onActions = onActions
            )

        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            NoteListScreenLandscape(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(start = 60.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(
                    start = 60.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(3),
                noteList = state.notes,
                onActions = onActions
            )
        }

        else -> {
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                noteList = state.notes,
                onActions = onActions,
            )
        }
    }

}