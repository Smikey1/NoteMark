package com.twugteam.admin.notemark

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterScreenRoot

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
        startDestination = "register",
        route = "auth"
    ) {
        composable(route = "register") {
            RegisterScreenRoot(
                onSuccessfulRegistration = {
                    navController.navigate("login"){
                        popUpTo(route = "register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState =true
                    // restoreState for login screen, if with mistakenly navigate
                    }
                },
                onAlreadyHaveAnAccountClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "login") {
            Text(
                text = "LOGIN"
            )
        }
    }
}

