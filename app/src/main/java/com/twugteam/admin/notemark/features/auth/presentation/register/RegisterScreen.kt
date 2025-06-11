package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import timber.log.Timber

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
            Timber.tag("WindowSize").d("Compact")
            RegisterScreenPortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("WindowSize").d("Medium")
            RegisterScreenTablet(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            Timber.tag("WindowSize").d("Expanded")
            RegisterScreenLandscape(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        //else
        else -> {
            Timber.tag("WindowSize").d("Else")
            RegisterScreenPortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

    }

}


