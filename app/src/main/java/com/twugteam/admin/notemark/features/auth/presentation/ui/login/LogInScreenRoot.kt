package com.twugteam.admin.notemark.features.auth.presentation.ui.login

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.landscape.LogInScreenMobileLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.landscape.LogInScreenTabletLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.portrait.LogInScreenMobilePortrait
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.portrait.LogInScreenTabletPortrait
import timber.log.Timber

@Composable
fun LogInScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    when(deviceConfiguration){
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Timber.tag("WindowSize").d("MOBILE_PORTRAIT")
            LogInScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Timber.tag("WindowSize").d("MOBILE_LANDSCAPE")
            LogInScreenMobileLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
        DeviceConfiguration.TABLET_PORTRAIT -> {
            Timber.tag("WindowSize").d("TABLET_PORTRAIT")
            LogInScreenTabletPortrait(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
        DeviceConfiguration.TABLET_LANDSCAPE -> {
            Timber.tag("WindowSize").d("TABLET_LANDSCAPE")
            LogInScreenTabletLandscape(
                modifier = modifier,
                state = state,
                onActions = onActions
            )
        }
        DeviceConfiguration.DESKTOP -> {
            Timber.tag("WindowSize").d("DESKTOP")

        }
    }
}