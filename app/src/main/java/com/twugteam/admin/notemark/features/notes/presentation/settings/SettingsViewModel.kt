package com.twugteam.admin.notemark.features.notes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.core.presentation.ui.asUiText
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

class SettingsViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _state: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<SettingsEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(settingsActions: SettingsActions) {
        when (settingsActions) {
            SettingsActions.OnBack -> onBack()
            SettingsActions.OnLogout -> onLogout()
        }
    }

    private fun onBack() {
        viewModelScope.launch {
            _events.send(SettingsEvents.OnBack)
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            val logOut = noteRepository.logOut()
            when (logOut) {
                is Result.Error -> {
                    Timber.tag("MyTag").e("logOut: ${logOut.error}")
                    showSnackBar(snackBarText = logOut.error.asUiText())
                }

                is Result.Success -> {
                    Timber.tag("MyTag").d("logOut: success")
                    _events.send(SettingsEvents.OnLogout)
                }
            }
        }
    }

    private fun showSnackBar(snackBarText: UiText) {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    showSnackBar = true,
                    snackBarText = snackBarText
                )
            }
            //show snackBar for 2 seconds
            delay(2.seconds)

            //reset state
            resetUiState()
        }
    }

    private fun resetUiState(){
        _state.value = SettingsUiState()
    }
}