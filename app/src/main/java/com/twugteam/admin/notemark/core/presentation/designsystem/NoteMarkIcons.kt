package com.twugteam.admin.notemark.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.twugteam.admin.notemark.R

object NoteMarkIcons {
    val EyeClosed: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.eye_closed)

    val EyeOpened: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.eye_opened)

    val Add: ImageVector
        get() = Icons.Default.Add

    val Close: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.close)

    val Logout: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.logout)

    val Settings: ImageVector
        get() = Icons.Default.Settings

    val Back: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.back)

    val CloudOff: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.cloudoff)
}