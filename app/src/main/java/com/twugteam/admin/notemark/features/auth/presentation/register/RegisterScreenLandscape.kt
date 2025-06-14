package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkInputTextField
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkNoOutlineActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkSharedScreen

@Composable
fun RegisterScreenLandscape(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    NoteMarkSharedScreen(
        modifier = modifier,
        title = R.string.create_account,
        description = R.string.capture_your_thoughts_and_ideas,
        isPortrait = false,
        showSnackBar = state.showSnackBar,
        snackBarText = state.snackBarText
    ) { contentModifier ->
        Column(
            modifier = contentModifier
        ) {
            //USERNAME
            NoteMarkInputTextField(
                modifier = Modifier,
                inputLabel = R.string.username,
                hint = R.string.example_username,
                inputValue = state.username.value,
                showLabel = true,
                isTrailingShowing = false,
                enabled = true,
                supportingText = state.username.supportingText,
                isError = state.username.isError,
                errorText = state.username.errorText,
                onValueChange = { username ->
                    onAction(RegisterAction.UpdateUsername(username = username))
                },
            )

            //EMAIL
            NoteMarkInputTextField(
                modifier = Modifier,
                inputLabel = R.string.email,
                hint = R.string.example_email,
                inputValue = state.email.value,
                showLabel = true,
                isTrailingShowing = false,
                enabled = true,
                supportingText = state.email.supportingText,
                isError = state.email.isError,
                errorText = state.email.errorText,
                onValueChange = { email ->
                    onAction(RegisterAction.UpdateEmail(email = email))
                },
            )

            //PASSWORD
            NoteMarkInputTextField(
                modifier = Modifier,
                inputLabel = R.string.password,
                hint = R.string.password,
                inputValue = state.password.value,
                showLabel = true,
                isTrailingShowing = true,
                enabled = true,
                supportingText = state.password.supportingText,
                isError = state.password.isError,
                errorText = state.password.errorText,
                onValueChange = { password ->
                    onAction(RegisterAction.UpdatePassword(password = password))
                },
            )

            //CONFIRM PASSWORD
            NoteMarkInputTextField(
                modifier = Modifier,
                inputLabel = R.string.repeat_password,
                hint = R.string.password,
                inputValue = state.confirmPassword.value,
                showLabel = true,
                isTrailingShowing = true,
                enabled = true,
                isLastField = true,
                supportingText = state.confirmPassword.supportingText,
                isError = state.confirmPassword.isError,
                errorText = state.confirmPassword.errorText,
                onValueChange = { confirmPassword ->
                    onAction(RegisterAction.UpdateConfirmPassword(confirmPassword = confirmPassword))
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            NoteMarkActionButton(
                text = stringResource(R.string.create_account),
                enabled = state.canRegister,
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            NoteMarkNoOutlineActionButton(
                text = stringResource(R.string.already_have_an_account),

                enabled = true,
                onClick = {
                    onAction(RegisterAction.OnAlreadyHaveAnAccountClick)
                }
            )
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun RegisterScreenLandscapePreview() {
    NoteMarkTheme {
        RegisterScreenLandscape(
            state = RegisterState(),
            onAction = {}
        )
    }
}