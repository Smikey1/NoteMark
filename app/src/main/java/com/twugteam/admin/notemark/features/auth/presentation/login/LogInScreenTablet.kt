package com.twugteam.admin.notemark.features.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkInputTextField
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkNoOutlineActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkSharedScreen

@Composable
fun LogInScreenTablet(
    modifier: Modifier = Modifier,
    state: LogInUiState,
    onActions: (LogInActions) -> Unit,
) {
    NoteMarkSharedScreen(
        modifier = modifier,
        portraitModifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 120.dp)
            .padding(vertical = 100.dp),
        title = R.string.login,
        description = R.string.capture_your_thoughts_and_ideas,
        snackBarText = state.snackBarText,
        showSnackBar = state.showSnackBar,
        ) { contentModifier ->
        Column(
            modifier = contentModifier
        ) {
            //Email
            NoteMarkInputTextField(
                modifier = Modifier,
                inputLabel = R.string.email,
                hint = R.string.example_email,
                enabled = state.isEnabled && !state.isLoading,
                inputValue = state.email,
                onValueChange = { emailValue ->
                    onActions(LogInActions.UpdateEmail(emailValue = emailValue))
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
                    onActions(LogInActions.UpdatePassword(passwordValue = passwordValue))
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
                    onActions(LogInActions.OnLogInClick)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            NoteMarkNoOutlineActionButton(
                text = stringResource(R.string.dont_have_account),
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isEnabled,
                isLoading = false,
                onClick = {
                    onActions(LogInActions.OnDontHaveAccountClick)
                }
            )
        }
    }
}