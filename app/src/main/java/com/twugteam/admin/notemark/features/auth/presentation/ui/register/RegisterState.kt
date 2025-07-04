package com.twugteam.admin.notemark.features.auth.presentation.ui.register

import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.R

data class RegisterState(
    val username: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        supportingText = UiText.StringResource(R.string.username_info,3,20),
        errorText= UiText.StringResource(R.string.username_error,3),
        isError= false,
    ),
    val email: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        errorText= UiText.StringResource(R.string.invalid_email),
        isError= false,
    ),
    val password: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        supportingText = UiText.StringResource(R.string.password_info,8),
        errorText= UiText.StringResource(R.string.password_error,8),
        isError= false,
    ),
    val confirmPassword: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        errorText= UiText.StringResource(R.string.password_do_not_match),
        isError= false,
    ),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarText: String = ""
)

data class InputFieldState(
    val value: String = "",
    val isValid: Boolean = false,
    val supportingText: UiText? = null,
    val errorText: UiText = UiText.DynamicString(""),
    val isError: Boolean = false
)
