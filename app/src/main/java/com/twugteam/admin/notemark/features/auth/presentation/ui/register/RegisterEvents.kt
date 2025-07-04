package com.twugteam.admin.notemark.features.auth.presentation.ui.register

import com.twugteam.admin.notemark.core.presentation.ui.UiText

sealed interface RegisterEvents {
    data class RegistrationError(val error: UiText) : RegisterEvents
    data object RegistrationSuccess : RegisterEvents
    data object NavigateToLogin : RegisterEvents
}