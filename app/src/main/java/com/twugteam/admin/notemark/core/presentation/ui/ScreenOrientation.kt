package com.twugteam.admin.notemark.core.presentation.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration

sealed interface DeviceType {
    data object Phone : DeviceType
    data object Tablet : DeviceType
    data object Desktop : DeviceType
}

sealed interface Orientation {
    data object Portrait : Orientation
    data object Landscape : Orientation
}

data class DeviceInfo(
    val deviceType: DeviceType,
    val orientation: Orientation
)

val LocalDeviceInfo = compositionLocalOf { DeviceInfo(DeviceType.Phone, Orientation.Portrait) }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProvideDeviceInfo(activity: Activity, content: @Composable () -> Unit) {
    val configuration = LocalConfiguration.current
    val smallestWidthDp = configuration.smallestScreenWidthDp
    val windowSizeClass = calculateWindowSizeClass(activity)

    val orientation = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> Orientation.Landscape
        Configuration.ORIENTATION_PORTRAIT -> Orientation.Portrait
        else -> Orientation.Portrait
    }

//    val deviceType = when (windowSizeClass.widthSizeClass) {
//        WindowWidthSizeClass.Compact -> DeviceType.Phone
//        WindowWidthSizeClass.Medium -> DeviceType.Tablet
//        WindowWidthSizeClass.Expanded -> DeviceType.Desktop
//        else -> DeviceType.Phone
//    }

    val deviceType = if (smallestWidthDp >= 600) DeviceType.Tablet else DeviceType.Phone

    CompositionLocalProvider(
        LocalDeviceInfo provides DeviceInfo(deviceType, orientation)
    ) {
        content()
    }
}
