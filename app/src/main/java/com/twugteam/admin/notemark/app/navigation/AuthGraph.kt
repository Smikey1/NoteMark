package com.twugteam.admin.notemark.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
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
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingEvents
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingScreenRoot
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInEvent
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInScreenRoot
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInViewModel
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterEvent
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterScreenRoot
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
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

            LandingScreenRoot(
                modifier = Modifier.fillMaxSize(),
                windowSizeClass = windowSizeClass,
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

            LogInScreenRoot(
                modifier = modifier.fillMaxSize(),
                windowSizeClass = windowSizeClass,
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
                windowSizeClass = windowSizeClass,
                state = registerState,
                onActions = registerViewModel::onAction
            )
        }
    }
}