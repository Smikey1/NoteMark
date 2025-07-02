package com.twugteam.admin.notemark.core.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthClass = windowSizeClass.widthSizeClass
            val heightClass = windowSizeClass.heightSizeClass
            return when {
                widthClass == WindowWidthSizeClass.Compact
                        && heightClass == WindowHeightSizeClass.Medium -> MOBILE_PORTRAIT

                widthClass == WindowWidthSizeClass.Compact
                        && heightClass == WindowHeightSizeClass.Expanded -> MOBILE_PORTRAIT

                widthClass == WindowWidthSizeClass.Expanded
                        && heightClass == WindowHeightSizeClass.Compact -> MOBILE_LANDSCAPE

                widthClass == WindowWidthSizeClass.Medium
                        && heightClass == WindowHeightSizeClass.Expanded -> TABLET_PORTRAIT

                widthClass == WindowWidthSizeClass.Expanded
                        && heightClass == WindowHeightSizeClass.Medium -> TABLET_LANDSCAPE


                widthClass == WindowWidthSizeClass.Compact
                        && heightClass == WindowHeightSizeClass.Compact -> MOBILE_LANDSCAPE


                widthClass == WindowWidthSizeClass.Medium
                        && heightClass == WindowHeightSizeClass.Compact -> MOBILE_LANDSCAPE

                widthClass == WindowWidthSizeClass.Medium
                        && heightClass == WindowHeightSizeClass.Medium -> TABLET_PORTRAIT


                widthClass == WindowWidthSizeClass.Expanded
                        && heightClass == WindowHeightSizeClass.Expanded -> TABLET_PORTRAIT

                else -> {
                    DESKTOP
                }
            }
        }
    }
}