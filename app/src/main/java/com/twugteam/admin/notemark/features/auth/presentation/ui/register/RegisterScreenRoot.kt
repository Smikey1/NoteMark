package com.twugteam.admin.notemark.features.auth.presentation.ui.register

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.landscape.RegisterScreenMobileLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.landscape.RegisterScreenTabletLandscape
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.portrait.RegisterScreenMobilePortrait
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.portrait.RegisterScreenTabletPortrait

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: RegisterState,
    onActions: (RegisterActions) -> Unit,
) {
    val deviceConfiguration =
        DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            RegisterScreenMobilePortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            RegisterScreenMobileLandscape(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            RegisterScreenTabletPortrait(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            RegisterScreenTabletLandscape(
                modifier = modifier,
                state = state,
                onAction = onActions
            )
        }

        DeviceConfiguration.DESKTOP -> {

        }
    }
}


