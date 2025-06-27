package com.twugteam.admin.notemark.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteList.viewmodel.NoteListEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.noteList.viewmodel.NoteListViewModel
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.viewmodel.UpsertNoteEvents
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.viewmodel.UpsertNoteViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

fun NavGraphBuilder.noteGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSize: WindowWidthSizeClass
) {
    navigation<Screens.NoteGraph>(
        startDestination = Screens.NoteList,
    ) {
        composable<Screens.NoteList> {
            val noteListViewModel = koinViewModel<NoteListViewModel>()
            val noteListState by noteListViewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvents(noteListViewModel.events) { event ->
                when (event) {
                    is NoteListEvents.NavigateToUpsertNote -> navController.navigate(
                        Screens.UpsertNote(
                            noteId = event.noteId,
                            isEdit = false
                        )
                    )
                }
            }
            NoteListScreenRoot(
                modifier = modifier,
                windowSize = windowSize,
                state = noteListState,
                onActions = noteListViewModel::onActions
            )
        }

        composable<Screens.UpsertNote> { entry ->
            val upsertNoteViewModel = koinViewModel<UpsertNoteViewModel>()
            val state by upsertNoteViewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvents(upsertNoteViewModel.events) { event ->
                Timber.tag("MyTag").d("event: $event")
                when (event) {
                    UpsertNoteEvents.Close -> navController.navigateUp()
                }
            }

            UpsertNoteScreenRoot(
                modifier = modifier,
                windowSize = windowSize,
                state = state,
                onActions = upsertNoteViewModel::onActions,
            )
        }
    }
}