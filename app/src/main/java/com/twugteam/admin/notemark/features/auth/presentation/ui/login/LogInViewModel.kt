package com.twugteam.admin.notemark.features.auth.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.twugteam.admin.notemark.R

class LogInViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state: MutableStateFlow<LogInUiState> = MutableStateFlow(LogInUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<LogInEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(logInActions: LogInActions) {
        when (logInActions) {
            is LogInActions.UpdateEmail -> updateEmail(emailValue = logInActions.emailValue)
            is LogInActions.UpdatePassword -> updatePassword(passwordValue = logInActions.passwordValue)
            LogInActions.LogInClick -> logIn()
            LogInActions.DontHaveAccountClick -> onDontHaveAccountClick()
        }
    }

    private fun updateEmail(emailValue: String) {
        //we check if email valid to check state of logIn button enabled or not
        if (userDataValidator.isValidEmail(emailValue)) {
            _state.update { newState ->
                newState.copy(email = emailValue)
            }
            if (_state.value.password.isNotBlank()) {
                _state.update { newState ->
                    newState.copy(canLogIn = true)
                }
            }
        } else {
            //if email not valid just update the email without updating logIn button state
            _state.update { newState ->
                newState.copy(email = emailValue, canLogIn = false)
            }
        }
    }

    private fun updatePassword(passwordValue: String) {
        //we check if email valid to check state of logIn button enabled or not
        if (userDataValidator.isValidEmail(_state.value.email)) {
            _state.update { newState ->
                newState.copy(password = passwordValue)
            }
            if (passwordValue.isNotBlank()) {
                _state.update { newState ->
                    newState.copy(canLogIn = true)
                }
            } else {
                _state.update { newState ->
                    newState.copy(canLogIn = false)
                }
            }
        } else {
            //if email not valid just update the password without updating logIn button state
            _state.update { newState ->
                newState.copy(password = passwordValue, canLogIn = false)
            }
        }
    }

    private fun logIn() {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(isEnabled = false, isLoading = true)
            }
            val result = authRepository.login(
                email = _state.value.email.trim(),
                password = _state.value.password
            )

            _state.update { newState ->
                newState.copy(isEnabled = true, isLoading = false)
            }
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        showSnackBar(errorMessage = UiText.StringResource(R.string.error_email_password_incorrect))
                    } else {
                        showSnackBar(errorMessage = result.error.asUiText())
                    }
                }

                is Result.Success -> _events.send(LogInEvents.LoginSuccess)
            }
        }
    }

    private fun onDontHaveAccountClick() {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(isEnabled = false, isLoading = true)
            }
            _events.send(element = LogInEvents.NavigateToRegister)
            _state.update { newState ->
                newState.copy(isEnabled = true, isLoading = false)
            }
        }
    }

    private fun showSnackBar(errorMessage: UiText) {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    showSnackBar = true,
                    snackBarText = errorMessage
                )
            }
            //show snackBar for 2 seconds
            delay(2.seconds)
            _state.update { newState ->
                newState.copy(
                    showSnackBar = false,
                    snackBarText = UiText.DynamicString("")
                )
            }
        }
    }
}