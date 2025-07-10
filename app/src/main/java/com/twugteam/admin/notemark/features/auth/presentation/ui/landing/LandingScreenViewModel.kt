package com.twugteam.admin.notemark.features.auth.presentation.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LandingScreenViewModel() : ViewModel() {
    private val _events = Channel<LandingEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(landingActions: LandingActions) {
        viewModelScope.launch {
            when (landingActions) {
                LandingActions.OnClickGetStarted -> onClickGetStarted()
                LandingActions.OnClickLogIn -> onClickLogIn()
            }
        }
    }

    private suspend fun onClickGetStarted() {
        _events.send(LandingEvents.NavigateToRegisterScreen)
    }

    private suspend fun onClickLogIn() {
        _events.send(LandingEvents.NavigateToLogInScreen)
    }

}