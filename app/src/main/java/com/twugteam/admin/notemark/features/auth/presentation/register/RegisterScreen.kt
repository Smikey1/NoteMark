package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    state: RegisterState,
    onActions: (RegisterAction) -> Unit,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            RegisterScreenPortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            RegisterScreenTablet(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            RegisterScreenLandscape(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        else -> {
            RegisterScreenPortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

    }

}


