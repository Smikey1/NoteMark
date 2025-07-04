package com.twugteam.admin.notemark.features.notes.presentation.noteList.util

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration

object TextUtil {
    fun textLimit(
        text: String,
        windowSizeClass: WindowSizeClass,
    ): String {
        val deviceConfiguration =
            DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

        val maxLength = when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> 150
            DeviceConfiguration.MOBILE_LANDSCAPE -> 150
            DeviceConfiguration.TABLET_PORTRAIT -> 250
            DeviceConfiguration.TABLET_LANDSCAPE -> 250
            DeviceConfiguration.DESKTOP -> 250
        }
        return if (text.length <= maxLength) text else text.take(maxLength) + "..."
    }
}