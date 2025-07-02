package com.twugteam.admin.notemark.features.auth.presentation.ui.login.designSystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkInputTextField
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkNoOutlineActionButton
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInAction
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInUiState

@Composable
fun LogInContent(
    modifier: Modifier,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        //Email
        NoteMarkInputTextField(
            modifier = Modifier,
            inputLabel = R.string.email,
            hint = R.string.example_email,
            enabled = state.isEnabled && !state.isLoading,
            inputValue = state.email,
            onValueChange = { emailValue ->
                onActions(LogInAction.UpdateEmail(emailValue = emailValue))
            },
            isTrailingShowing = false,
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Password
        NoteMarkInputTextField(
            modifier = Modifier,
            inputLabel = R.string.password,
            hint = R.string.password,
            enabled = state.isEnabled && !state.isLoading,
            inputValue = state.password,
            isLastField = true,
            onValueChange = { passwordValue ->
                onActions(LogInAction.UpdatePassword(passwordValue = passwordValue))
            },
            isTrailingShowing = true,
        )

        Spacer(modifier = Modifier.height(24.dp))

        NoteMarkActionButton(
            text = stringResource(R.string.login),
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isEnabled && state.isLogInEnabled,
            isLoading = state.isLoading,
            onClick = {
                onActions(LogInAction.LogInClick)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        NoteMarkNoOutlineActionButton(
            text = stringResource(R.string.dont_have_account),
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isEnabled,
            isLoading = false,
            onClick = {
                onActions(LogInAction.DontHaveAccountClick)
            }
        )
    }
}