package com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.landscape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkSharedScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterAction
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterState
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.designSystem.components.RegisterContent

@Composable
fun RegisterScreenTabletLandscape(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    NoteMarkSharedScreen(
        modifier = modifier,
        title = R.string.create_account,
        description = R.string.capture_your_thoughts_and_ideas,
        isPortrait = false,
        halfContent = false,
        showSnackBar = state.showSnackBar,
        snackBarText = state.snackBarText
    ) { contentModifier ->
        RegisterContent(
            modifier = contentModifier,
            state = state,
            onAction = onAction
        )
    }
}