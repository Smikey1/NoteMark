package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.core.domain.auth.SessionStorage
import com.twugteam.admin.notemark.core.presentation.ui.getInitial
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNoteUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface NoteListAction {
    data class NavigateToUpsertNote(val noteId: String?) : NoteListAction
    data class OnNoteDelete(val noteId: String) : NoteListAction
    data object OnDialogConfirm : NoteListAction
    data object OnDialogDismiss : NoteListAction
}

sealed interface NoteListEvents {
    data class NavigateToUpsertNote(val noteId: String?) : NoteListEvents
}

class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage,
) : ViewModel() {
    private val _state: MutableStateFlow<NoteListState> = MutableStateFlow(NoteListState())
    val state = _state.onStart {
        viewModelScope.launch {
            getUsername()
            getNotes()
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = NoteListState(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    private suspend fun getUsername() {
        val username = sessionStorage.getAuthInto()?.username
        val usernameInitial = username?.getInitial() ?: "PL"

        _state.update { newState ->
            newState.copy(
                username = usernameInitial
            )
        }
    }

    private val _events = Channel<NoteListEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(noteListAction: NoteListAction) {
        viewModelScope.launch {
            when (noteListAction) {
                is NoteListAction.NavigateToUpsertNote -> navigateToUpsertNote(noteId = noteListAction.noteId)
                is NoteListAction.OnNoteDelete -> noteToDelete(noteId = noteListAction.noteId)
                NoteListAction.OnDialogConfirm -> deleteNote()
                NoteListAction.OnDialogDismiss -> onDialogCancel()
            }
        }
    }

    private suspend fun deleteNote() {
        val noteToDeleteId = _state.value.noteToDeleteId
        showLoader(showLoader = true)
        noteRepository.deleteNoteById(id = noteToDeleteId!!)
        showLoader(showLoader = false)
        showDialog(showDialog = false)
    }

    private fun showLoader(showLoader: Boolean) {
        _state.update { newState->
            newState.copy(
                isLoading =  showLoader
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

    private fun showDialog(showDialog: Boolean){
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

    private suspend fun navigateToUpsertNote(noteId: String?) {
        _events.send(NoteListEvents.NavigateToUpsertNote(noteId))
    }

}