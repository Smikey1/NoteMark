package com.twugteam.admin.notemark.features.auth.presentation.ui.register

sealed interface RegisterActions {
    data class UpdateUsername(val username: String) : RegisterActions
    data class UpdateEmail(val email: String) : RegisterActions
    data class UpdatePassword(val password: String) : RegisterActions
    data class UpdateConfirmPassword(val confirmPassword: String) : RegisterActions
    data object OnRegisterClick : RegisterActions
    data object OnAlreadyHaveAnAccountClick : RegisterActions
}
