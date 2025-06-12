package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.features.auth.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

sealed interface RegisterAction {
    data class UpdateUsername(val username: String) : RegisterAction
    data class UpdateEmail(val email: String) : RegisterAction
    data class UpdatePassword(val password: String) : RegisterAction
    data class UpdateConfirmPassword(val confirmPassword: String) : RegisterAction
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object OnToggleConfirmPasswordVisibilityClick : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnAlreadyHaveAnAccountClick : RegisterAction
}

sealed interface RegisterEvent {
    data class Error(val error: UiText) : RegisterEvent
    data object RegistrationSuccess : RegisterEvent
    data object NavigateToLogin : RegisterEvent
}

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        viewModelScope.launch {
            when (action) {
                is RegisterAction.UpdateUsername -> updateUsername(action.username)
                is RegisterAction.UpdateEmail -> updateEmail(action.email)
                is RegisterAction.UpdatePassword -> updatePassword(action.password)
                is RegisterAction.UpdateConfirmPassword -> updateConfirmPassword(action.confirmPassword)
                RegisterAction.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
                RegisterAction.OnToggleConfirmPasswordVisibilityClick -> toggleConfirmPasswordVisibility()
                RegisterAction.OnRegisterClick -> register()
                RegisterAction.OnAlreadyHaveAnAccountClick -> navigateToLogin()
            }
        }
    }

    private fun updateUsername(username: String) {
        val isValid = userDataValidator.isUsernameValid(username)
        _state.update {
            it.copy(
                username = username,
                isUserNameValid = isValid,
                canRegister = isValid && it.isEmailValid && it.passwordValidationState.isPasswordValid && it.isConfirmPasswordValid
            )
        }
    }

    private fun updateEmail(email: String) {
        val isValid = userDataValidator.isValidEmail(email)
        _state.update {
            it.copy(
                email = email,
                isEmailValid = isValid,
                canRegister = isValid && it.isUserNameValid && it.passwordValidationState.isPasswordValid && it.isConfirmPasswordValid
            )
        }
    }

    private fun updatePassword(password: String) {
        val validationState = userDataValidator.validatePassword(password)
        val isConfirmValid = userDataValidator.isValidConfirmPassword(password, _state.value.confirmPassword)
        _state.update {
            it.copy(
                password = password,
                passwordValidationState = validationState,
                isConfirmPasswordValid = isConfirmValid,
                canRegister = it.isUserNameValid && it.isEmailValid && validationState.isPasswordValid && isConfirmValid
            )
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        val isValid = userDataValidator.isValidConfirmPassword(_state.value.password, confirmPassword)
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                isConfirmPasswordValid = isValid,
                canRegister = it.isUserNameValid && it.isEmailValid && it.passwordValidationState.isPasswordValid && isValid
            )
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        _state.update {
            it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible)
        }
    }

    private suspend fun register() {
        _state.update { it.copy(isRegistering = true) }
        delay(2.seconds)

        // TODO: Replace with actual API call and error handling
        _state.update { it.copy(isRegistering = false) }
        _eventChannel.send(RegisterEvent.RegistrationSuccess)
    }

    private suspend fun navigateToLogin() {
        _eventChannel.send(RegisterEvent.NavigateToLogin)
    }
}
