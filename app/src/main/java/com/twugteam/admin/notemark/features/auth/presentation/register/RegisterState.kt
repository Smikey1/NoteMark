package com.twugteam.admin.notemark.features.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.twugteam.admin.notemark.features.auth.domain.PasswordValidationState

data class RegisterState(
    val username: TextFieldState = TextFieldState(),
    val isUserNameValid: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val isConfirmPasswordVisible: Boolean = false,
    val isConfirmPasswordValid: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false

)
