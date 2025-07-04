package com.twugteam.admin.notemark.features.auth.presentation.ui.login

sealed interface LogInActions {
    data class UpdateEmail(val emailValue: String) : LogInActions
    data class UpdatePassword(val passwordValue: String) : LogInActions
    data object LogInClick : LogInActions
    data object DontHaveAccountClick : LogInActions
}