package com.twugteam.admin.notemark.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
}