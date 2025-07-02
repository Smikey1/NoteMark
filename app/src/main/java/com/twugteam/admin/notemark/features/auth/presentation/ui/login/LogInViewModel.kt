package com.twugteam.admin.notemark.features.auth.presentation.ui.login

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

sealed interface LogInAction {
    data class UpdateEmail(val emailValue: String) : LogInAction
    data class UpdatePassword(val passwordValue: String) : LogInAction
    data object LogInClick : LogInAction
    data object DontHaveAccountClick : LogInAction
}

sealed interface LogInEvent {
    data object NavigateToRegister : LogInEvent
    data object LoginSuccess : LogInEvent
    data class Error(val error: UiText) : LogInEvent

}

class LogInViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _logInUiState: MutableStateFlow<LogInUiState> = MutableStateFlow(LogInUiState())
    val logInUiState = _logInUiState.asStateFlow()

    private val _events = Channel<LogInEvent>()
    val events = _events.receiveAsFlow()

    fun onActions(logInAction: LogInAction) {
        viewModelScope.launch {
            when (logInAction) {
                is LogInAction.UpdateEmail -> updateEmail(emailValue = logInAction.emailValue)
                is LogInAction.UpdatePassword -> updatePassword(passwordValue = logInAction.passwordValue)
                LogInAction.LogInClick -> logIn()
                LogInAction.DontHaveAccountClick -> onDontHaveAccountClick()
            }
        }
    }

    private fun updateEmail(emailValue: String) {
        if (userDataValidator.isValidEmail(emailValue)) {
            _logInUiState.update { newState ->
                newState.copy(email = emailValue)
            }
            if (_logInUiState.value.password.isNotBlank()) {
                _logInUiState.update { newState ->
                    newState.copy(isLogInEnabled = true)
                }
            }
        } else {
            _logInUiState.update { newState ->
                newState.copy(email = emailValue, isLogInEnabled = false)
            }
        }
    }

    private fun updatePassword(passwordValue: String) {
        if (userDataValidator.isValidEmail(_logInUiState.value.email)) {
            _logInUiState.update { newState ->
                newState.copy(password = passwordValue)
            }
            if (passwordValue.isNotBlank()) {
                _logInUiState.update { newState ->
                    newState.copy(isLogInEnabled = true)
                }
            } else {
                _logInUiState.update { newState ->
                    newState.copy(isLogInEnabled = false)
                }
            }
        } else {
            _logInUiState.update { newState ->
                newState.copy(password = passwordValue, isLogInEnabled = false)
            }
        }
    }

    private fun logIn() {
        viewModelScope.launch {
            _logInUiState.update { newState ->
                newState.copy(isEnabled = false, isLoading = true)
            }
            val result = authRepository.login(
                email = _logInUiState.value.email.trim(),
                password = _logInUiState.value.password
            )
            
            _logInUiState.update { newState ->
                newState.copy(isEnabled = true, isLoading = false)
            }
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _events.send(
                            LogInEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _events.send(LogInEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> _events.send(LogInEvent.LoginSuccess)
            }
        }
    }

    private suspend fun onDontHaveAccountClick() {
        _logInUiState.update { newState ->
            newState.copy(isEnabled = false, isLoading = true)
        }
        _events.send(element = LogInEvent.NavigateToRegister)
        _logInUiState.update { newState ->
            newState.copy(isEnabled = true, isLoading = false)
        }
    }

    fun showSnackBar(errorMessage: String) {
        viewModelScope.launch {
            _logInUiState.update { newState ->
                newState.copy(
                    showSnackBar = true,
                    snackBarText = errorMessage
                )
            }
            delay(2.seconds)
            _logInUiState.update { newState ->
                newState.copy(
                    showSnackBar = false,
                    snackBarText = ""
                )
            }
        }
    }
}