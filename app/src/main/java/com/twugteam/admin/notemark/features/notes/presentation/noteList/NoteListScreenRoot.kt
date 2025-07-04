package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.features.notes.presentation.noteList.orientationScreens.NoteListScreenLandscape
import com.twugteam.admin.notemark.features.notes.presentation.noteList.orientationScreens.NoteListScreenMobilePortrait
import com.twugteam.admin.notemark.features.notes.presentation.noteList.orientationScreens.NoteListScreenTablet

@Composable
fun NoteListScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    windowSize: WindowWidthSizeClass,
    state: NoteListState,
    onActions: (NoteListActions) -> Unit,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            NoteListScreenTablet(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                noteMarkListPaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )

        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            NoteListScreenLandscape(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(start = 60.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                noteMarkListPaddingValues = PaddingValues(
                    start = 60.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(3),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }

        else -> {
            NoteListScreenMobilePortrait(
                modifier = modifier,
                topBarPaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                state = state,
                onActions = onActions,
                windowSizeClass = windowSizeClass
            )
        }
    }

}