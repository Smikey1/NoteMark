package com.twugteam.admin.notemark.features.auth.presentation.landing.orientationScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkOutlineActionButton
import com.twugteam.admin.notemark.features.auth.presentation.landing.LandingActions

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
        LandingBottomComponent(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClickGetStarted = onClickGetStarted,
            onClickLogIn = onClickLogIn
        )
    }
}

@Composable
fun LandingBottomComponent(
    modifier: Modifier = Modifier,
    onClickGetStarted: () -> Unit,
    onClickLogIn: () -> Unit,
) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
            color = SurfaceLowest
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 32.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.your_own_collection_of_notes),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.capture_your_thoughts_and_ideas),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(40.dp))

                NoteMarkActionButton(
                    text = stringResource(R.string.get_started),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    onClick = onClickGetStarted
                )

                Spacer(modifier = Modifier.height(12.dp))


                NoteMarkOutlineActionButton(
                    text = stringResource(R.string.login),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    onClick = onClickLogIn
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
    }
}