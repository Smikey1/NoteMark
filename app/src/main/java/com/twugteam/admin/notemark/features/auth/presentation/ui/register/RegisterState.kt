package com.twugteam.admin.notemark.features.auth.presentation.ui.register

data class RegisterState(
    val username: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        supportingText = "Use between 3 and 20 characters for your username",
        errorText= "Username must be at least 3 characters",
        isError= false,
    ),
    val email: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        errorText= "Invalid email provided",
        isError= false,
    ),
    val password: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        supportingText = "Use 8+ characters with a number or symbol for better security",
        errorText= "Password must be at least 8 characters and include a number or symbol",
        isError= false,
    ),
    val confirmPassword: InputFieldState = InputFieldState(
        value = "",
        isValid= false,
        errorText= "Passwords do not match",
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
    val supportingText: String? = null,
    val errorText: String = "",
    val isError: Boolean = false
)
