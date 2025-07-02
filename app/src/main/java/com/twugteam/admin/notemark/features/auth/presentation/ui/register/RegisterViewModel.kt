package com.twugteam.admin.notemark.features.auth.presentation.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.domain.util.DataError
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.core.presentation.ui.asUiText
import com.twugteam.admin.notemark.features.auth.domain.AuthRepository
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
    data object OnRegisterClick : RegisterAction
    data object OnAlreadyHaveAnAccountClick : RegisterAction
}

sealed interface RegisterEvent {
    data class RegistrationError(val error: UiText) : RegisterEvent
    data object RegistrationSuccess : RegisterEvent
    data object NavigateToLogin : RegisterEvent
}

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
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
                RegisterAction.OnRegisterClick -> register()
                RegisterAction.OnAlreadyHaveAnAccountClick -> navigateToLogin()
            }
        }
    }

    private fun updateUsername(username: String) {
        val validityState = userDataValidator.isUsernameValid(username)
        _state.update {
            it.copy(
                username = it.username.copy(
                    value = username,
                    isValid = validityState.isValid,
                    isError = if (username.isNotBlank()) !validityState.isValid else false,
                    errorText = validityState.errorText
                )
            )
        }
        val canRegister = canRegister()
        _state.update { newState ->
            newState.copy(canRegister = canRegister)
        }
    }

    private fun updateEmail(email: String) {
        val isValid = userDataValidator.isValidEmail(email)
        _state.update {
            it.copy(
                email = it.email.copy(
                    value = email,
                    isValid = isValid,
                    isError = if (email.isNotBlank()) !isValid else false,
                ),
            )
        }
        val canRegister = canRegister()
        _state.update { newState ->
            newState.copy(canRegister = canRegister)
        }
    }

    private fun updatePassword(password: String) {
        val isValid = userDataValidator.validatePassword(password)
        _state.update {
            it.copy(
                password = it.password.copy(
                    value = password,
                    isValid = isValid,
                    isError = !isValid,
                ),
            )
        }
        val canRegister = canRegister()
        _state.update { newState ->
            newState.copy(canRegister = canRegister)
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        val isValid = userDataValidator.isValidConfirmPassword(
            password = _state.value.password.value,
            confirmPassword = confirmPassword
        )
        _state.update {
            it.copy(
                confirmPassword = it.confirmPassword.copy(
                    value = confirmPassword,
                    isValid = isValid,
                    isError = !isValid
                ),
            )
        }

        val canRegister = canRegister()
        _state.update { newState ->
            newState.copy(canRegister = canRegister)
        }
    }

    private suspend fun register() {
        _state.update { it.copy(isRegistering = true) }

        val result = authRepository.register(
            username = _state.value.username.value,
            email = _state.value.email.value,
            password = _state.value.password.value
        )

        when (result) {
            is Result.Error -> {
                if (result.error == DataError.Network.CONFLICT) {
                    _eventChannel.send(
                        RegisterEvent.RegistrationError(
                            error = UiText.StringResource(
                                R.string.error_conflict
                            )
                        )
                    )
                } else {
                    _eventChannel.send(RegisterEvent.RegistrationError(error = result.error.asUiText()))
                }
                _state.update { it.copy(isRegistering = false) }
            }

            is Result.Success -> {
                //clear state
                resetState()
                _eventChannel.send(RegisterEvent.RegistrationSuccess)
            }
        }


    }


    private fun canRegister(): Boolean {
        val state = _state.value
        return state.username.isValid && state.email.isValid && state.password.isValid && (state.password.value == state.confirmPassword.value)
    }

    private suspend fun navigateToLogin() {
        _eventChannel.send(RegisterEvent.NavigateToLogin)
    }

    fun showSnackBar(errorMessage: String) {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    showSnackBar = true,
                    snackBarText = errorMessage
                )
            }
            delay(2.seconds)
            _state.update { newState ->
                newState.copy(
                    showSnackBar = false,
                    snackBarText = ""
                )
            }
        }
    }

    private fun resetState() {
        _state.value = RegisterState()
    }
}
