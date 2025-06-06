package com.twugteam.admin.notemark

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

@Composable
fun NavigationRoot(
    isLoggedInPreviously: Boolean,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedInPreviously) "notes" else "auth"
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {

        }
    }
}

