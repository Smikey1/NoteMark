package com.twugteam.admin.notemark.features.notes.presentation.noteList.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

object Util {
    fun textLimit(
        text: String,
        windowSize: WindowWidthSizeClass
    ): String {
        val maxLength = when (windowSize) {
            WindowWidthSizeClass.Compact -> 150
            WindowWidthSizeClass.Medium -> 250
            WindowWidthSizeClass.Expanded -> 150
            else -> 150
        }
        return if (text.length <= maxLength) text else text.take(maxLength) + "..."
    }
}