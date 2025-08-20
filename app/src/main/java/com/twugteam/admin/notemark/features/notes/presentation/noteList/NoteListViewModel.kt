package com.twugteam.admin.notemark.features.notes.presentation.noteList

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.domain.network.ConnectivityObserver
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.toNoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.util.getInitial
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("MissingPermission")
class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage,
    private val connectivityObserver: ConnectivityObserver,
    pager: Pager<Int, NoteEntity>
) : ViewModel() {
    private val _state: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<NoteListEvents>()
    val events = _events.receiveAsFlow()

    //mapping pager from NoteEntity to NoteUi
    val notesPagingFlow = pager.flow.map { pagingData ->
        pagingData.map { entity ->
            entity.toNoteUi()
        }
    }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            //getNetworkConnectivity(), getUsername() and getNotesPaging() run in parallel
            //because getNetworkConnectivity() and getNotesPaging() launch in a viewmodelScope.launch{}
            getNetworkConnectivity()
            //getNotesPaging()
            getNotesPaging()
            getUsername()
        }
    }

    private fun getNotesPaging() {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    notesPagingFlow = notesPagingFlow
                )
            }
        }
    }

    fun onActions(noteListActions: NoteListActions) {
        when (noteListActions) {
            is NoteListActions.NavigateToNoteDetail -> navigateToNoteDetail(noteId = noteListActions.noteId)
            is NoteListActions.OnNoteDelete -> noteToDelete(noteId = noteListActions.noteId)
            NoteListActions.OnDialogConfirm -> deleteNote()
            NoteListActions.OnDialogDismiss -> onDialogCancel()
            NoteListActions.NavigateToSettings -> navigateToSettings()
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
                dialogLoading = showLoader
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