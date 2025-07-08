package com.twugteam.admin.notemark.features.auth.presentation.ui.landing.designSystem.components.landscape

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R

@Composable
fun LandingLandscapeComponent(
    modifier: Modifier = Modifier,
    onClickGetStarted: () -> Unit,
    onClickLogIn: () -> Unit,
) {
    Row(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.weight(1f).padding(start = 19.dp, end = 15.dp),
            painter = painterResource(R.drawable.landing_screen),
            contentDescription = stringResource(R.string.landing_screen),
            contentScale = ContentScale.FillWidth
        )

        LandingLandscapeBottomComponent(
            modifier = Modifier
                .weight(1.2f),
            onClickGetStarted = onClickGetStarted,
            onClickLogIn = onClickLogIn
        )
    }
}
