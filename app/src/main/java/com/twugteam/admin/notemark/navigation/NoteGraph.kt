package com.twugteam.admin.notemark.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.noteGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController, windowSize: WindowWidthSizeClass
) {
    navigation<Screens.NoteGraph>(
        startDestination = Screens.Home,
    ) {
        composable<Screens.Home> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text("Note Graph ")
            }
        }
    }
}