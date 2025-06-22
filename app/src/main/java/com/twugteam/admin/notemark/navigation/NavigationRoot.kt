package com.twugteam.admin.notemark.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    isLoggedInPreviously: Boolean,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = if (isLoggedInPreviously) Screens.NoteGraph else Screens.AuthGraph
    ) {
        authGraph(modifier = modifier, navController = navController, windowSize = windowSize)

        noteGraph(
            modifier = modifier,
            navController = navController,
            windowSize = windowSize
        )
    }
}



