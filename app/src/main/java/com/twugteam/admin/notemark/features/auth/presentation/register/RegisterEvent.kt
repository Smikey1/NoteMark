package com.twugteam.admin.notemark.features.auth.presentation.register

import com.twugteam.admin.notemark.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data class Error(val error: UiText) : RegisterEvent
    data object RegistrationSuccess : RegisterEvent
}