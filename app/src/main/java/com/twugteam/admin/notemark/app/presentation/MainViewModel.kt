package com.twugteam.admin.notemark.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {
    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch {
            //sequentially because refreshTokenExpired is only needed after logging in
            //and performing actions
            getAuthInfo()
            getRefreshTokenExpired()
        }
    }

    private suspend fun getAuthInfo() {
        isCheckingAuth(isCheckingAuth = true)
        val authInfo = sessionStorage.getAuthInfo()
        Timber.tag("MyTag").d("authInfo: $authInfo")
        _mainState.update { newState ->
            newState.copy(
                isLoggedInPreviously = authInfo != null
            )
        }
        isCheckingAuth(isCheckingAuth = false)
    }

    private suspend fun getRefreshTokenExpired(){
        sessionStorage.getRefreshTokenExpired().collectLatest { refreshTokenExpired->
            Timber.tag("MyTag").d("refreshTokenExpired: $refreshTokenExpired")
            _mainState.update { newState->
                newState.copy(refreshTokenExpired = refreshTokenExpired)
            }
        }
    }

    private fun isCheckingAuth(isCheckingAuth: Boolean) {
        _mainState.update { newState ->
            newState.copy(
                isCheckingAuth = isCheckingAuth
            )
        }
    }

}