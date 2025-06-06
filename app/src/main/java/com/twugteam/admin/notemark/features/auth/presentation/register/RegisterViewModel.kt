package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.features.auth.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val username = snapshotFlow { state.username.text }
    private val email = snapshotFlow { state.email.text }
    private val password = snapshotFlow { state.password.text }
    private val confirmPassword = snapshotFlow { state.confirmPassword.text }

    private var _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            username.onEach { usernameChar ->
                val isUsernameValid = userDataValidator.isUsernameValid(usernameChar.toString())
                val isBothPasswordValid =
                    state.passwordValidationState.isPasswordValid && state.isConfirmPasswordValid
                val canRegister = state.isEmailValid && isBothPasswordValid && !state.isRegistering
                state = state.copy(
                    isUserNameValid = isUsernameValid,
                    canRegister = isUsernameValid && canRegister
                )
            }
            email.onEach { emailChar ->
                val isEmailValid = userDataValidator.isValidEmail(emailChar.toString())
                val isBothPasswordValid =
                    state.passwordValidationState.isPasswordValid && state.isConfirmPasswordValid
                val canRegister =
                    state.isUserNameValid && isBothPasswordValid && !state.isRegistering
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canRegister = isEmailValid && canRegister
                )
            }.launchIn(viewModelScope)
            password.onEach { passwordChar ->
                val passwordValidationState =
                    userDataValidator.validatePassword(passwordChar.toString())
                val canRegister =
                    state.isUserNameValid && state.isEmailValid && state.isConfirmPasswordValid && !state.isRegistering
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = passwordValidationState.isPasswordValid && canRegister
                )
            }.launchIn(viewModelScope)
            confirmPassword.onEach { confirmPasswordChar ->
                val isConfirmPasswordValid = userDataValidator.isValidConfirmPassword(
                    state.password.text.toString(),
                    confirmPasswordChar.toString()
                )
                val isBothPasswordValid =
                    state.passwordValidationState.isPasswordValid && isConfirmPasswordValid
                val canRegister =
                    state.isUserNameValid && isBothPasswordValid && !state.isRegistering
                state = state.copy(
                    isConfirmPasswordValid = isConfirmPasswordValid,
                    canRegister = state.isEmailValid && canRegister
                )
            }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnToggleConfirmPasswordVisibilityClick -> {
                state = state.copy(isConfirmPasswordVisible = !state.isConfirmPasswordVisible)
            }

            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            //TODO: Register Api call
            state = state.copy(isRegistering = false)
        }
    }
}