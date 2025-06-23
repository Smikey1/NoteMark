package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNote
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

sealed interface UpsertNoteActions {
    data class UpdateNoteUiTitle(val noteTitle: String) : UpsertNoteActions
    data class UpdateNoteUiContent(val noteContent: String) : UpsertNoteActions
    data class SaveNote(val noteUi: NoteUi) : UpsertNoteActions
    data object Close : UpsertNoteActions
}

sealed interface UpsertNoteEvents {
    data object Close : UpsertNoteEvents
}

class UpsertNoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state: MutableStateFlow<UpsertNoteState> =
        MutableStateFlow(UpsertNoteState())
    val state = _state.asStateFlow()

    private val _events = Channel<UpsertNoteEvents>()
    val events = _events.receiveAsFlow()

    init {
        val noteId: String? = savedStateHandle.get<String>("noteId")
        Timber.tag("MyTag").d("noteId: $noteId")
        viewModelScope.launch {
            getNoteById(noteId = noteId)
        }
    }

   private suspend fun getNoteById(noteId: String?) {
       if(noteId.isNullOrEmpty()){
        _state.update { newState->
            newState.copy(
                noteUi = null,
                isEdit = false
            )
        }
           return
       }
        noteRepository.getNoteById(id = noteId).collectLatest { note ->
            _state.update { newState ->
                newState.copy(
                    noteUi = note.toNoteUi(),
                    isEdit = true
                )
            }

        }
    }


    fun onActions(upsertNoteActions: UpsertNoteActions) {
        viewModelScope.launch {
            when (upsertNoteActions) {
                UpsertNoteActions.Close -> close()
                is UpsertNoteActions.SaveNote -> saveNote(noteUi = upsertNoteActions.noteUi)
                is UpsertNoteActions.UpdateNoteUiContent -> updateNoteUiContent(noteContent = upsertNoteActions.noteContent)
                is UpsertNoteActions.UpdateNoteUiTitle -> updateNoteUiTitle(noteTitle = upsertNoteActions.noteTitle)
            }
        }
    }

    private suspend fun close() {
        _events.send(UpsertNoteEvents.Close)
    }

    private suspend fun saveNote(noteUi: NoteUi) {
        noteRepository.upsertNote(note = noteUi.toNote())
    }

    private fun updateNoteUiTitle(noteTitle: String) {
        Timber.tag("MyTag").d("noteTitle: $noteTitle")
        _state.update { newState ->
            newState.copy(
                noteUi = newState.noteUi!!.copy(title = noteTitle)
            )
        }
    }

    private fun updateNoteUiContent(noteContent: String) {
        _state.update { newState ->
            newState.copy(
                noteUi = newState.noteUi!!.copy(content = noteContent)
            )
        }
    }
}