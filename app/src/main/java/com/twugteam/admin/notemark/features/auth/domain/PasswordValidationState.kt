package com.twugteam.admin.notemark.features.auth.domain

data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasSymbol: Boolean = false
) {
    val isPasswordValid: Boolean
        get() = hasMinimumLength && (hasNumber || hasSymbol)

}
