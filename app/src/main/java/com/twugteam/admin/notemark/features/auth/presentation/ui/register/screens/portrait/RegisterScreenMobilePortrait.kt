package com.twugteam.admin.notemark.features.auth.presentation.ui.register.screens.portrait

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkAuthScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterActions
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.RegisterState
import com.twugteam.admin.notemark.features.auth.presentation.ui.register.designSystem.components.RegisterContent


@Composable
fun RegisterScreenMobilePortrait(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (RegisterActions) -> Unit
) {
    NoteMarkAuthScreen(
        modifier = modifier,
        paddingValues = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        title = R.string.create_account,
        description = R.string.capture_your_thoughts_and_ideas,
        showSnackBar = state.showSnackBar,
        snackBarText = state.snackBarText
    ){ contentModifier->
        RegisterContent(
            modifier = contentModifier,
            state = state,
            onAction = onAction
        )
        }
}

@Preview
@Composable
private fun RegisterScreenPortraitPreview() {
    NoteMarkTheme {
        RegisterScreenMobilePortrait(
            state = RegisterState(),
            onAction = {}
        )
    }
}