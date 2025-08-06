package com.twugteam.admin.notemark.app.presentation

data class MainState(
    val isCheckingAuth: Boolean = false,
    val isLoggedInPreviously: Boolean = false,
    val refreshTokenExpired: Boolean = false,
)
