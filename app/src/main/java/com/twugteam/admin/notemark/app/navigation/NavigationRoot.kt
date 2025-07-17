package com.twugteam.admin.notemark.app.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    isLoggedInPreviously: Boolean,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoggedInPreviously) Screens.NoteGraph else Screens.AuthGraph
    ) {
        authGraph(
            modifier = Modifier,
            navController = navController,
            windowSizeClass = windowSizeClass
        )

        noteGraph(
            modifier = Modifier.statusBarsPadding().navigationBarsPadding(),
            navController = navController,
            windowSizeClass = windowSizeClass
        )
    }
}



