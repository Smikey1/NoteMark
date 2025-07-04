package com.twugteam.admin.notemark.features.auth.presentation.ui.landing

sealed interface LandingActions{
    data object OnClickGetStarted: LandingActions
    data object OnClickLogIn: LandingActions
}
