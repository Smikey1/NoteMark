package com.twugteam.admin.notemark.features.auth.presentation.ui.login

sealed interface LogInEvents {
    data object NavigateToRegister : LogInEvents
    data object LoginSuccess : LogInEvents
}