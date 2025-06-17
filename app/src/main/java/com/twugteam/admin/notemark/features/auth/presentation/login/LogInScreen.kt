package com.twugteam.admin.notemark.features.auth.presentation.login

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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