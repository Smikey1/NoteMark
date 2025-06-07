package com.twugteam.admin.notemark.features.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkNoOutlineActionButton
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkPasswordTextField
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkTextField
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.auth.domain.UserDataValidator
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onAlreadyHaveAnAccountClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(flow = viewModel.events) { events ->
        when (events) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, events.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()
                onSuccessfulRegistration()
            }
        }
    }
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnAlreadyHaveAnAccountClick -> onAlreadyHaveAnAccountClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .fillMaxHeight(0.95f)
                .align(Alignment.BottomCenter),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 32.dp)
                    .padding(bottom = 16.dp),
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(R.string.capture_your_thoughts_and_ideas),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(40.dp))
                NoteMarkTextField(
                    state = state.username,
                    hint = stringResource(R.string.example_username),
                    title = stringResource(R.string.username),
                    additionalInfo = stringResource(R.string.username_info),
                    error = if (!state.isUserNameValid) stringResource(
                        R.string.username_error,
                        UserDataValidator.MIN_USERNAME_LENGTH
                    ) else null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(16.dp))
                NoteMarkTextField(
                    state = state.email,
                    hint = stringResource(R.string.example_email),
                    title = stringResource(R.string.email),
                    error = if (!state.isEmailValid) stringResource(R.string.invalid_email) else null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                NoteMarkPasswordTextField(
                    state = state.password,
                    hint = stringResource(R.string.password),
                    title = stringResource(R.string.password),
                    additionalInfo = stringResource(R.string.password_info),
                    error = if (!state.passwordValidationState.isPasswordValid) stringResource(
                        R.string.password_error,
                        UserDataValidator.MIN_PASSWORD_LENGTH
                    ) else null,
                    modifier = Modifier.fillMaxWidth(),
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibilityClick = {
                        onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                NoteMarkPasswordTextField(
                    state = state.confirmPassword,
                    hint = stringResource(R.string.password),
                    title = stringResource(R.string.repeat_password),
                    error = if (!state.isConfirmPasswordValid) stringResource(R.string.password_do_not_match) else null,
                    modifier = Modifier.fillMaxWidth(),
                    isPasswordVisible = state.isConfirmPasswordVisible,
                    onTogglePasswordVisibilityClick = {
                        onAction(RegisterAction.OnToggleConfirmPasswordVisibilityClick)
                    }
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
                    isLoading = false,
                    enabled = true,
                    onClick = {
                        onAction(RegisterAction.OnAlreadyHaveAnAccountClick)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    NoteMarkTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}
