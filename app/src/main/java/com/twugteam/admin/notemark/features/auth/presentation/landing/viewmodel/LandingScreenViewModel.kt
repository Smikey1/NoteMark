package com.twugteam.admin.notemark.features.auth.presentation.landing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface LandingActions{
    data object OnClickGetStarted: LandingActions
    data object OnClickLogIn: LandingActions
}

sealed interface LandingEvents{
    data object NavigateToRegisterScreen: LandingEvents
    data object NavigateToLogInScreen: LandingEvents}

class LandingScreenViewModel(): ViewModel() {
    private val _landingEvents = Channel<LandingEvents>()
    val landingEvents = _landingEvents.receiveAsFlow()

    fun onActions(landingActions: LandingActions){
        viewModelScope.launch {
            when(landingActions){
                LandingActions.OnClickGetStarted -> onClickGetStarted()
                LandingActions.OnClickLogIn -> onClickLogIn()
            }
        }
    }

    suspend fun onClickGetStarted(){
        _landingEvents.send(LandingEvents.NavigateToRegisterScreen)
    }

    suspend fun onClickLogIn(){
        _landingEvents.send(LandingEvents.NavigateToLogInScreen)
    }

}