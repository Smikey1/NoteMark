package com.twugteam.admin.notemark.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListViewModel
import org.koin.androidx.compose.koinViewModel

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
            NoteListScreenRoot(
                modifier = modifier,
                windowSize = windowSize,
                state = noteListState,
                onActions = {

                }
            )
        }
    }
}