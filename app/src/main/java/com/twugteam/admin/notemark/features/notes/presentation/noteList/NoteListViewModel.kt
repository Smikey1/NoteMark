package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}

sealed interface NoteListEvents {
    data class NavigateToUpsertNote(val noteId: String?) : NoteListEvents
}

class NoteListViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _state: MutableStateFlow<NoteListState> = MutableStateFlow(NoteListState())
    val state = _state.onStart {

    }.stateIn(
        scope = viewModelScope,
        initialValue = NoteListState(),
        started = SharingStarted.WhileSubscribed(5000)
    )

    private val _events = Channel<NoteListEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(noteListAction: NoteListAction) {
        viewModelScope.launch {
            when (noteListAction) {
                is NoteListAction.NavigateToUpsertNote -> navigateToUpsertNote(noteId = noteListAction.noteId)
            }
        }
    }


    private suspend fun getNotes() {
        noteRepository.getNotes().collect { note ->
            val noteList = note.map {
                it.toNoteUi()
            }
            _state.update { newState ->
                newState.copy(
                    notes = noteList
                )
            }
        }
    }

    private suspend fun navigateToUpsertNote(noteId: String?) {
        _events.send(NoteListEvents.NavigateToUpsertNote(noteId))
    }

}