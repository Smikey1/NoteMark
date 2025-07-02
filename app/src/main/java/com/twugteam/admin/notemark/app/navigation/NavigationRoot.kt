package com.twugteam.admin.notemark.app.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    windowSizeClass: WindowSizeClass,
    isLoggedInPreviously: Boolean,
    navController: NavHostController,

) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = if (isLoggedInPreviously) Screens.NoteGraph else Screens.AuthGraph
    ) {
        authGraph(
            modifier = modifier,
            navController = navController,
            windowSizeClass = windowSizeClass
        )

        noteGraph(
            modifier = modifier,
            navController = navController,
            windowSize = windowSize
        )
    }
}



