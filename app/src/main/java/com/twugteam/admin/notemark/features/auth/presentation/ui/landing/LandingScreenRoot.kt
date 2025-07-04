package com.twugteam.admin.notemark.features.auth.presentation.ui.landing

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.landscape.LandingScreenMobileLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.landscape.LandingScreenTabletLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.portrait.LandingScreenMobilePortrait
import com.twugteam.admin.notemark.features.auth.presentation.ui.landing.screens.portrait.LandingScreenTabletPortrait
import timber.log.Timber

@Composable
fun LandingScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    onActions: (LandingActions) -> Unit,
) {
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Timber.tag("WindowSize").d("MOBILE_PORTRAIT")
            LandingScreenMobilePortrait(
                modifier = modifier,
                onActions = onActions
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Timber.tag("WindowSize").d("MOBILE_LANDSCAPE")
            LandingScreenMobileLandscape(
                modifier = modifier,
                onActions = onActions
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            Timber.tag("WindowSize").d("TABLET_PORTRAIT")
            LandingScreenTabletPortrait(
                modifier = modifier,
                onActions = onActions
            )
        }
        DeviceConfiguration.TABLET_LANDSCAPE -> {
            Timber.tag("WindowSize").d("TABLET_LANDSCAPE")
            LandingScreenTabletLandscape(
                modifier = modifier.padding(bottom = 17.dp),
                onActions = onActions
            )
        }
        DeviceConfiguration.DESKTOP -> {
            Timber.tag("WindowSize").d("DESKTOP")
        }
    }
}