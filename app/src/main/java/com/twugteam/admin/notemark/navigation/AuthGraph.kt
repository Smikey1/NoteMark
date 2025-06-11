package com.twugteam.admin.notemark.navigation

import android.widget.Toast
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
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingEvents
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreen
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingScreenViewModel
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInEvents
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInScreen
import com.twugteam.admin.notemark.features.auth.presentation.login.LogInViewModel
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterEvent
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterScreenRoot
import com.twugteam.admin.notemark.features.auth.presentation.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(
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

            ObserveAsEvents(logInViewModel.events) { events ->
                when (events) {
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
            val registerViewModel = koinViewModel<RegisterViewModel>()
            val registerState by registerViewModel.registerState.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val keyboardController = LocalSoftwareKeyboardController.current

            ObserveAsEvents(registerViewModel.events) { events ->
                when (events) {
                    is RegisterEvent.Error -> {
                        keyboardController?.hide()
                        Toast.makeText(context, events.error.asString(context), Toast.LENGTH_SHORT)
                            .show()
                    }

                    RegisterEvent.RegistrationSuccess -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.registration_successful),
                            Toast.LENGTH_SHORT
                        ).show()
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