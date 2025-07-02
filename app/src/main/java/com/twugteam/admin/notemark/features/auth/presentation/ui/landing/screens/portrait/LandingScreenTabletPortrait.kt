package com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.portrait

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.LandingBackground
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.LandingActions
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.designSystem.components.portrait.LandingPortraitBottomComponent

@Composable
fun LandingScreenTabletPortrait(
    modifier: Modifier = Modifier,
    onActions: (LandingActions) -> Unit
) {
    LandingScreenTabletComponent(
        modifier = modifier
            .background(LandingBackground),
        onClickGetStarted = {
            onActions(LandingActions.OnClickGetStarted)
        },
        onClickLogIn = {
            onActions(LandingActions.OnClickLogIn)
        }
    )
}

@Composable
fun LandingScreenTabletComponent(
    modifier: Modifier = Modifier,
    onClickGetStarted: () -> Unit,
    onClickLogIn: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
            painter = painterResource(R.drawable.landing_screen),
            contentDescription = stringResource(R.string.landing_screen),
            contentScale = ContentScale.Fit
        )
        LandingPortraitBottomComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            columnPaddingValues = PaddingValues(
                top = 48.dp,
                start = 48.dp,
                end = 48.dp,
                bottom = 16.dp
            ),
            onClickGetStarted = onClickGetStarted,
            onClickLogIn = onClickLogIn
        )
    }
}
