package com.twugteam.admin.notemark.features.auth.presentation.landing.screens.portrait

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingActions
import com.twugteam.admin.notemark.features.auth.presentation.landing.components.portrait.LandingPortraitBottomComponent

@Composable
fun LandingScreenMobilePortrait(
    modifier: Modifier = Modifier,
    onActions: (LandingActions) -> Unit
) {
    LandingScreenMobilePortraitComponent(
        modifier = modifier,
        onClickGetStarted = {
            onActions(LandingActions.OnClickGetStarted)
        },
        onClickLogIn = {
            onActions(LandingActions.OnClickLogIn)
        },
    )
}

@Composable
fun LandingScreenMobilePortraitComponent(
    modifier: Modifier = Modifier,
    onClickGetStarted: () -> Unit,
    onClickLogIn: () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
            painter = painterResource(R.drawable.landing_screen),
            contentDescription = stringResource(R.string.landing_screen),
            contentScale = ContentScale.FillWidth
        )
        LandingPortraitBottomComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            columnPaddingValues = PaddingValues(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            onClickGetStarted = onClickGetStarted,
            onClickLogIn = onClickLogIn
        )
    }
}

