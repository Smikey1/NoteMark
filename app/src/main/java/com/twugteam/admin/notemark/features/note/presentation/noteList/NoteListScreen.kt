package com.twugteam.admin.notemark.features.note.presentation.noteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.features.note.presentation.model.NoteUi
import timber.log.Timber

val noteList = listOf(
    NoteUi(
        id = "1",
        title = "Title",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "2",
        title = "Title of the note",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue." +
                "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi s",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "3",
        title = "Title",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "4",
        title = "Title of the note",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "5",
        title = "Title",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    ),
    NoteUi(
        id = "6",
        title = "Title of the note",
        content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
        createdAt = "19 APR",
        lastEditedAt = "TODO()"
    )
)

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
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                username = "PL",
                noteMarkListPaddingValues = PaddingValues(16.dp),
                verticalSpace = 16.dp,
                horizontalSpace = 16.dp,
                staggeredGridCells = StaggeredGridCells.Fixed(2),
                noteList = noteList
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("WindowSize").d("Medium")
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
                noteList = noteList
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
                staggeredGridCells = StaggeredGridCells.Fixed(3),
                noteList = noteList
            )
        }

        //else
        else -> {
            Timber.tag("WindowSize").d("Else")
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
                noteList = noteList
            )
        }
    }

}