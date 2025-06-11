package com.twugteam.admin.notemark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingEvents
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreen
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInViewModel
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterScreenRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInEvents
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInScreen

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
        startDestination = if (isLoggedInPreviously) Screens.NoteMark else Screens.AuthGraph
    ) {
        authGraph(modifier = modifier, navController = navController, windowSize = windowSize)
    }
}

private fun NavGraphBuilder.authGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController, windowSize: WindowWidthSizeClass
) {
    navigation<Screens.AuthGraph>(
        startDestination = Screens.Landing,
    ) {
        composable<Screens.Landing> {
            val landingViewModel = koinViewModel<LandingScreenViewModel>()

            ObserveAsEvents(flow = landingViewModel.landingEvents) { events ->
                when (events) {
                    LandingEvents.NavigateToLogInScreen -> navController.navigate(Screens.LogIn)
                    LandingEvents.NavigateToRegisterScreen -> navController.navigate(Screens.Register)
                }
            }

            LandingScreen(
                modifier = Modifier.fillMaxSize(),
                windowSize = windowSize,
                onActions = landingViewModel::onActions
            )
        }

        composable<Screens.LogIn> {
            val logInViewModel = koinViewModel<LogInViewModel>()
            val logInUiState by logInViewModel.logInUiState.collectAsStateWithLifecycle()

            ObserveAsEvents(logInViewModel.events) { events->
                when(events){
                    LogInEvents.NavigateToRegister -> navController.navigate(Screens.Register)
                }
            }
            LogInScreen(
                modifier = modifier.fillMaxSize(),
                windowSize = windowSize,
                state = logInUiState,
                onActions = logInViewModel::onActions
            )

        }

        composable<Screens.Register> {
            RegisterScreenRoot(
                onSuccessfulRegistration = {
                    navController.navigate("login") {
                        popUpTo(route = "register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        // restoreState for login screen, if with mistakenly navigate
                    }
                },
                onAlreadyHaveAnAccountClick = {
                    navController.navigate("login")
                }
            )
        }
    }
}

@Serializable
sealed interface Screens {

    //auth navGraph
    @Serializable
    data object AuthGraph : Screens

    //auth screens
    @Serializable
    data object Landing : Screens

    @Serializable
    data object LogIn : Screens

    @Serializable
    data object Register : Screens

    //noteMark navGraph
    @Serializable
    data object NoteMark : Screens

}

