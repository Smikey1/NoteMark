package com.twugteam.admin.notemark.features.auth.presentation.login

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
import timber.log.Timber

sealed interface LogInActions {
    data class UpdateEmail(val emailValue: String) : LogInActions
    data class UpdatePassword(val passwordValue: String) : LogInActions
    data object OnLogInClick : LogInActions
    data object OnDontHaveAccountClick : LogInActions
}

sealed interface LogInEvents {
    data object NavigateToRegister : LogInEvents
    data object LoginSuccess : LogInEvents
    data class Error(val error: UiText): LogInEvents

}

class LogInViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _logInUiState: MutableStateFlow<LogInUiState> = MutableStateFlow(LogInUiState())
    val logInUiState = _logInUiState.asStateFlow()

    private val _events = Channel<LogInEvents>()
    val events = _events.receiveAsFlow()

    init {
        Timber.tag("viewModel").d("LogIn: initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag("viewModel").d("LogIn: Cleared!")

    }

    fun onActions(logInActions: LogInActions) {
        viewModelScope.launch {
            when (logInActions) {
                is LogInActions.UpdateEmail -> updateEmail(emailValue = logInActions.emailValue)
                is LogInActions.UpdatePassword -> updatePassword(passwordValue = logInActions.passwordValue)
                LogInActions.OnLogInClick -> logIn()
                LogInActions.OnDontHaveAccountClick -> onDontHaveAccountClick()
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
            Timber.tag("MyTag").d(passwordValue)
            if (passwordValue.isNotBlank()) {
                Timber.tag("MyTag").d("here")
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

    private suspend fun logIn() {
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
                        _events.send(LogInEvents.Error(
                            UiText.StringResource(R.string.error_email_password_incorrect)
                        ))
                    } else {
                        _events.send(LogInEvents.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> _events.send(LogInEvents.LoginSuccess)
            }
        }
    }

    private suspend fun onDontHaveAccountClick() {
        _logInUiState.update { newState ->
            newState.copy(isEnabled = false, isLoading = true)
        }
        _events.send(element = LogInEvents.NavigateToRegister)
        _logInUiState.update { newState ->
            newState.copy(isEnabled = true, isLoading = false)
        }
    }
}