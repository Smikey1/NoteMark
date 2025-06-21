package com.twugteam.admin.notemark.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.features.note.presentation.noteList.NoteListScreen

fun NavGraphBuilder.noteGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSize: WindowWidthSizeClass
) {
    navigation<Screens.NoteGraph>(
        startDestination = Screens.NoteList,
    ) {
        composable<Screens.NoteList> {
            NoteListScreen(
                modifier = modifier,
                windowSize = windowSize
            )
        }
    }
}