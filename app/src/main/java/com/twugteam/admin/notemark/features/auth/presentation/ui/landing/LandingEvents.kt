package com.twugteam.admin.notemark.features.auth.presentation.ui.landing

sealed interface LandingEvents {
    data object NavigateToRegisterScreen : LandingEvents
    data object NavigateToLogInScreen : LandingEvents
}