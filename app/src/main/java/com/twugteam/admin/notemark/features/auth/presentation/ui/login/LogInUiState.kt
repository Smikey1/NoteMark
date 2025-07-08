package com.twugteam.admin.notemark.features.auth.presentation.ui.login

import com.twugteam.admin.notemark.core.presentation.ui.UiText

data class LogInUiState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val canLogIn: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarText: UiText = UiText.DynamicString("")
)