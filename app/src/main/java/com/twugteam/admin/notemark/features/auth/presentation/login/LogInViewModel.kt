package com.twugteam.admin.notemark.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

}

class LogInViewModel(
    private val userDataValidator: UserDataValidator,
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
            }else{
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
        _logInUiState.update { newState ->
            newState.copy(isEnabled = false, isLoading = true)
        }
        delay(2000)
        _logInUiState.update { newState ->
            newState.copy(isEnabled = true, isLoading = false)
        }
        _logInUiState.update { newState->
            newState.copy(error = true)
        }
        delay(2000)
        _logInUiState.update { newState->
            newState.copy(error = false)
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