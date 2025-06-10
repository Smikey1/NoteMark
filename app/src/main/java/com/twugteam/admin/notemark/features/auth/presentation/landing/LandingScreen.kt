package com.twugteam.admin.notemark.features.auth.presentation.landing

import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.designsystem.LandingBackground
import timber.log.Timber

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    onActions: (LandingActions) -> Unit,
) {
    when (windowSize) {
        //MOBILE PORTRAIT
        WindowWidthSizeClass.Compact -> {
            Timber.tag("MyTag").d("Compact")
            LandingScreenMobilePortrait(
                modifier = modifier,
                onActions = onActions
            )
        }

        //TABLET
        WindowWidthSizeClass.Medium -> {
            Timber.tag("MyTag").d("Medium")
            LandingScreenTablet(
                modifier = modifier,
                onActions = onActions
            )
        }

        //LANDSCAPE
        WindowWidthSizeClass.Expanded -> {
            Timber.tag("MyTag").d("Expanded")
            LandingScreenLandscape(
                modifier = modifier.background(color = LandingBackground),
                onActions = onActions
            )
        }

        //else
        else -> {
            Timber.tag("MyTag").d("Else")
            LandingScreenMobilePortrait(
                modifier = modifier,
                onActions = onActions
            )
        }

    }


}