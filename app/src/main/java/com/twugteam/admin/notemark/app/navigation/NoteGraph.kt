package com.twugteam.admin.notemark.app.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListViewModel
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsEvents
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsViewModel
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteEvents
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.upsertNote.UpsertNoteViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@SuppressLint("RestrictedApi")
fun NavGraphBuilder.noteGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    navigation<Screens.NoteGraph>(
        startDestination = Screens.NoteList,
    ) {
        composable<Screens.NoteList> {
            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
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

                    NoteListEvents.NavigateToSettings -> navController.navigate(Screens.Settings)
                }
            }
            NoteListScreenRoot(
                modifier = modifier,
                windowSizeClass = windowSizeClass,
                state = noteListState,
                onActions = noteListViewModel::onActions
            )
        }

        composable<Screens.UpsertNote> { entry ->
            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
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
                state = state,
                windowSizeClass = windowSizeClass,
                onActions = upsertNoteViewModel::onActions,
            )
        }

        composable<Screens.Settings> {
            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            val state by settingsViewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvents(settingsViewModel.events) { event->
                when(event){
                    SettingsEvents.OnBack -> navController.navigateUp()
                    SettingsEvents.OnLogout -> navController.navigate(Screens.LogIn){
                        popUpTo(Screens.NoteGraph){
                            inclusive = true
                        }
                    }
                }
            }

            SettingsScreenRoot(
                modifier = modifier,
                windowSizeClass = windowSizeClass,
                state = state,
                onActions = settingsViewModel::onActions
            )
        }
    }
}