package com.twugteam.admin.notemark.features.auth.presentation.register

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object OnToggleConfirmPasswordVisibilityClick : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnAlreadyHaveAnAccountClick : RegisterAction

}