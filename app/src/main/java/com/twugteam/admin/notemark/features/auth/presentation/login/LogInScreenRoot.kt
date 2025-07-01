package com.twugteam.admin.notemark.features.auth.presentation.login

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenLandscape
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenMobilePortrait
import com.twugteam.admin.notemark.features.auth.presentation.login.orientationScreens.LogInScreenTablet
import timber.log.Timber

@Composable
fun LogInScreenRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            Timber.tag("WindowSize").d("Compact")
            LogInScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("WindowSize").d("Medium>")
            LogInScreenTablet(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            Timber.tag("WindowSize").d("Expanded")
            LogInScreenLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }

        //else
        else -> {
            Timber.tag("WindowSize").d("Else")
            LogInScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
    }


}