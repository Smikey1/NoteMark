package com.twugteam.admin.notemark.features.auth.presentation.login

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenLandscape
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenMobilePortrait
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenTablet
import com.twugteam.admin.notemark.features.auth.presentation.login.state.LogInUiState
import com.twugteam.admin.notemark.features.auth.presentation.login.viewmodel.LogInAction

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            LogInScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            LogInScreenTablet(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            LogInScreenLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //else
        else -> {
            LogInScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
    }


}