package com.twugteam.admin.notemark.features.auth.presentation.register

import com.twugteam.admin.notemark.features.auth.domain.PasswordValidationState

data class RegisterState(
    val username: String = "",
    val isUserNameValid: Boolean = false,
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val confirmPassword: String = "",
    val isConfirmPasswordVisible: Boolean = false,
    val isConfirmPasswordValid: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false

)
