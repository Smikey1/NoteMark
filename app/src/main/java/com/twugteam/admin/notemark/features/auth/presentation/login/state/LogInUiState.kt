package com.twugteam.admin.notemark.features.auth.presentation.login.state

data class LogInUiState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val isLogInEnabled: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarText: String = ""
)