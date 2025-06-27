package com.twugteam.admin.notemark.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.auth.presentation.landing.viewmodel.LandingEvents
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreen
import com.twugteam.admin.notemark.features.auth.presentation.landing.viewmodel.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.login.viewmodel.LogInEvent
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInScreen
import com.twugteam.admin.notemark.features.auth.presentation.login.viewmodel.LogInViewModel
import com.twugteam.admin.notemark.features.auth.presentation.register.viewmodel.RegisterEvent
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterScreenRoot
import com.twugteam.admin.notemark.features.auth.presentation.register.viewmodel.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSize: WindowWidthSizeClass
) {
    navigation<Screens.AuthGraph>(
        startDestination = Screens.Landing,
    ) {
        composable<Screens.Landing> {
            val landingViewModel = koinViewModel<LandingScreenViewModel>()

            ObserveAsEvents(flow = landingViewModel.landingEvents) { events ->
                when (events) {
                    LandingEvents.NavigateToLogInScreen -> {
                        navController.navigate(Screens.LogIn) {
                            popUpTo<Screens.Landing> {
                                inclusive = true
                            }
                        }
                    }

                    LandingEvents.NavigateToRegisterScreen -> {
                        navController.navigate(Screens.Register) {
                            popUpTo<Screens.Landing> {
                                inclusive = true
                            }
                        }
                    }
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
            val context = LocalContext.current
            ObserveAsEvents(logInViewModel.events) { events ->
                when (events) {
                    LogInEvent.NavigateToRegister -> navController.navigate(Screens.Register) {
                        popUpTo<Screens.LogIn> {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }

                    is LogInEvent.Error -> {
                        logInViewModel.showSnackBar(errorMessage = events.error.asString(context))
                    }

                    LogInEvent.LoginSuccess -> {
                        navController.navigate(Screens.NoteGraph){
                            popUpTo(0){
                                inclusive = true
                                saveState = false
                            }
                        }
                    }
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
            val registerViewModel = koinViewModel<RegisterViewModel>()
            val registerState by registerViewModel.state.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current

            ObserveAsEvents(registerViewModel.events) { events ->
                when (events) {
                    is RegisterEvent.RegistrationError -> {
                        keyboardController?.hide()
                        registerViewModel.showSnackBar(errorMessage = events.error.asString(context))
                    }

                    RegisterEvent.RegistrationSuccess -> {
                        registerViewModel.showSnackBar(errorMessage = context.getString(R.string.registration_successful))
                        navController.navigate(Screens.LogIn){
                            popUpTo<Screens.Register>{
                                inclusive = true
                                saveState = false
                            }
                            restoreState = false
                        }
                    }

                    RegisterEvent.NavigateToLogin -> navController.navigate(Screens.LogIn) {
                        popUpTo<Screens.Register> {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            }

            RegisterScreenRoot(
                modifier = modifier.fillMaxSize(),
                windowSize = windowSize,
                state = registerState,
                onActions = registerViewModel::onAction
            )
        }
    }
}