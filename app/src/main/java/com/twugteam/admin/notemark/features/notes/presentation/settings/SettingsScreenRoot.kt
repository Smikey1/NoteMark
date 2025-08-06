package com.twugteam.admin.notemark.features.notes.presentation.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.features.notes.presentation.settings.screens.landscape.SettingsScreenMobileLandscape
import com.twugteam.admin.notemark.features.notes.presentation.settings.screens.landscape.SettingsScreenTabletLandscape
import com.twugteam.admin.notemark.features.notes.presentation.settings.screens.portrait.SettingsScreenMobilePortrait
import com.twugteam.admin.notemark.features.notes.presentation.settings.screens.portrait.SettingsScreenTabletPortrait

@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    state: SettingsUiState,
    onActions: (SettingsActions) -> Unit
) {
    val deviceType = DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

    when (deviceType) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            SettingsScreenMobilePortrait(
                modifier = modifier,
                scaffoldPaddingValues = PaddingValues(start = 16.dp),
                topBarPaddingValues = PaddingValues(top = 16.dp),
                contentPaddingValues = PaddingValues(16.dp),
                state = state,
                onActions = onActions
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            SettingsScreenMobileLandscape(
                modifier = modifier,
                scaffoldPaddingValues = PaddingValues(start = 60.dp),
                topBarPaddingValues = PaddingValues(top = 12.dp),
                contentPaddingValues = PaddingValues(16.dp),
                state = state,
                onActions = onActions
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            SettingsScreenTabletPortrait(
                modifier = modifier,
                scaffoldPaddingValues = PaddingValues(start = 24.dp),
                topBarPaddingValues = PaddingValues(top = 16.dp),
                contentPaddingValues = PaddingValues(16.dp),
                state = state,
                onActions = onActions
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            SettingsScreenTabletLandscape(
                modifier = modifier,
                scaffoldPaddingValues = PaddingValues(start = 60.dp),
                topBarPaddingValues = PaddingValues(top = 12.dp),
                contentPaddingValues = PaddingValues(16.dp),
                state = state,
                onActions = onActions
            )
        }

        DeviceConfiguration.DESKTOP -> {

        }
    }
}
