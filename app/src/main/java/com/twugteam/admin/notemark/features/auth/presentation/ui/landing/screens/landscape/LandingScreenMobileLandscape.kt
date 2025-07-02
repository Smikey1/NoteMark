package com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.landscape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingActions
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.designSystem.components.landscape.LandingLandscapeComponent

@Composable
fun LandingScreenMobileLandscape(
    modifier: Modifier = Modifier,
    onActions: (LandingActions) -> Unit
) {
    LandingLandscapeComponent(
        modifier = modifier,
        onClickGetStarted = {
            onActions(LandingActions.OnClickGetStarted)
        },
        onClickLogIn = {
            onActions(LandingActions.OnClickLogIn)
        }
    )
}

