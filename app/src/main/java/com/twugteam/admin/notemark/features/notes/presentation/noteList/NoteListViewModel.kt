package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.network.ConnectivityObserver
import com.twugteam.admin.notemark.core.presentation.ui.toNoteUi
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.util.getInitial
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _state: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            //getNetworkConnectivity(), getUsername() and getNotes() run in parallel
            //because getNetworkConnectivity() and getNotes() launch in a viewmodelScope.launch{}
            getNetworkConnectivity()
            getNotes()
            getUsername()
        }
    }

    private fun getNetworkConnectivity() {
        viewModelScope.launch {
            connectivityObserver.isConnected.collect { isConnected ->
                Timber.tag("NetworkConnectivity").d("viewModel: $isConnected")
                _state.update { newState ->
                    newState.copy(
                        isConnected = isConnected
                    )
                }
            }
        }
    }

    private suspend fun getUsername() {
        val username = sessionStorage.getAuthInfo()?.username
        val usernameInitial = username?.getInitial() ?: "PL"

        _state.update { newState ->
            newState.copy(
                username = usernameInitial
            )
        }
    }

    private val _events = Channel<NoteListEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(noteListActions: NoteListActions) {
        when (noteListActions) {
            is NoteListActions.NavigateToNoteDetail -> navigateToNoteDetail(noteId = noteListActions.noteId)
            is NoteListActions.OnNoteDelete -> noteToDelete(noteId = noteListActions.noteId)
            NoteListActions.OnDialogConfirm -> deleteNote()
            NoteListActions.OnDialogDismiss -> onDialogCancel()
            NoteListActions.NavigateToSettings -> navigateToSettings()
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            val noteToDeleteId = _state.value.noteToDeleteId
            showLoader(showLoader = true)
            noteRepository.deleteNoteById(id = noteToDeleteId!!)
            showLoader(showLoader = false)
            showDialog(showDialog = false)
        }
    }

    private fun showLoader(showLoader: Boolean) {
        _state.update { newState ->
            newState.copy(
                isLoading = showLoader
            )
        }
    }

    private fun onDialogCancel() {
        _state.update { newState ->
            newState.copy(
                noteToDeleteId = null,
            )
        }
        showDialog(showDialog = false)
    }

    private fun noteToDelete(noteId: String) {
        _state.update { newState ->
            newState.copy(
                noteToDeleteId = noteId,
            )
        }
        showDialog(showDialog = true)
    }

    private fun showDialog(showDialog: Boolean) {
        _state.update { newState ->
            newState.copy(
                showDialog = showDialog
            )
        }
    }


    private fun getNotes() {
        viewModelScope.launch {
            noteRepository.getNotes().collect { note ->
                val noteList = note.map {
                    it.toNoteUi()
                }
                _state.update { newState ->
                    newState.copy(
                        notes = noteList,
                    )
                }
            }
        }
    }

    private fun navigateToNoteDetail(noteId: String?) {
        viewModelScope.launch {
            _events.send(NoteListEvents.NavigateToNoteDetail(noteId))
        }
    }

    private fun navigateToSettings() {
        viewModelScope.launch {
            _events.send(NoteListEvents.NavigateToSettings)
        }
    }
}