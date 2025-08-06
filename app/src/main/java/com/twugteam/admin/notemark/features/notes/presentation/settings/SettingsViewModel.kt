package com.twugteam.admin.notemark.features.notes.presentation.settings

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.core.presentation.ui.asUiText
import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.domain.SyncIntervalDataStore
import com.twugteam.admin.notemark.core.domain.SyncRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

class SettingsViewModel(
    private val noteRepository: NoteRepository,
    private val syncRepository: SyncRepository,
    private val syncIntervalDataStore: SyncIntervalDataStore,
) : ViewModel() {
    private val _state: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<SettingsEvents>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            //read interval and timeUnit from dataStore
            getInterval()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun onActions(settingsActions: SettingsActions) {
        when (settingsActions) {
            SettingsActions.OnBack -> onBack()
            SettingsActions.OnLogout -> onLogout()
            SettingsActions.OnExpand -> onExpand()
            is SettingsActions.SyncWithInterval -> onSyncIntervalSelect(syncInterval = settingsActions.syncInterval)
            SettingsActions.ManualSync -> syncData()
        }
    }

    private suspend fun getInterval() {
        syncIntervalDataStore.getInterval().collectLatest { (interval,text, timeUnit) ->
            _state.update { newState ->
                newState.copy(
                    selectedSyncingInterval = newState.selectedSyncingInterval.copy(
                        interval = interval,
                        text = text,
                        timeUnit = timeUnit
                    )
                )
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun syncData() {
        viewModelScope.launch {
            syncRepository.manualSync()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun syncWithInterval(interval: Long?, timeUnit: TimeUnit?) {
        viewModelScope.launch {
            syncRepository.syncWithInterval(interval = interval, timeUnit = timeUnit)
        }
    }


    private fun onExpand() {
        val isExpanded = _state.value.isSyncExpanded
        _state.update { newState ->
            newState.copy(
                isSyncExpanded = !isExpanded
            )
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun onSyncIntervalSelect(syncInterval: SyncInterval) {
        viewModelScope.launch {
            val interval = syncInterval.interval
            val text = syncInterval.text
            val timeUnit = syncInterval.timeUnit

            //save into interval data store
            val saveIntoDataStore = syncIntervalDataStore.saveInterval(
                interval = interval,
                text = text,
                timeUnit = timeUnit
            )

            when (saveIntoDataStore) {
                is Result.Error -> {
                    Timber.tag("MyTag")
                        .e("onSyncIntervalSelect: error : ${saveIntoDataStore.error}")
                }

                is Result.Success -> {
                    _state.update { newState ->
                        newState.copy(
                            selectedSyncingInterval = newState.selectedSyncingInterval.copy(
                                text = syncInterval.text
                            )
                        )
                    }
                    //sync after saving
                    syncWithInterval(
                        interval = syncInterval.interval,
                        timeUnit = syncInterval.timeUnit
                    )
                }
            }

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
                    showSnackBar(snackBarText = logOut.error.asUiText())
                }

                is Result.Success -> {

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

    private fun resetUiState() {
        _state.value = SettingsUiState()
    }
}