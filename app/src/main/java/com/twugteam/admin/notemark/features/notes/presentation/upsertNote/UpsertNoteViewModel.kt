package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.domain.util.asEmptyDataResult
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNote
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZonedDateTime

sealed interface UpsertNoteActions {
    data class UpdateNoteUiTitle(val noteTitle: String) : UpsertNoteActions
    data class UpdateNoteUiContent(val noteContent: String) : UpsertNoteActions
    data class SaveNote(val noteUi: NoteUi) : UpsertNoteActions
    data object OnDialogDismiss : UpsertNoteActions
    data object OnSaveNoteClick : UpsertNoteActions
    data object OnCloseIconClick : UpsertNoteActions
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
        if (noteId.isNullOrEmpty()) {
            _state.update { newState ->
                newState.copy(
                    noteUi = NoteUi(
                        id = null,
                        title = "",
                        content = "",
                        createdAt = ZonedDateTime.now(),
                        lastEditedAt = ZonedDateTime.now()
                    ),
                    isEdit = false
                )
            }
            return
        }
        val note = noteRepository.getNoteById(id = noteId)
        _state.update { newState ->
            newState.copy(
                noteUi = note.toNoteUi(),
                isEdit = true,
            )
        }
        val noteUi = _state.value.noteUi
        Timber.tag("MyTag").d("noteUi: $noteUi")
    }


    fun onActions(upsertNoteActions: UpsertNoteActions) {
        viewModelScope.launch {
            when (upsertNoteActions) {
                UpsertNoteActions.Close -> close()
                is UpsertNoteActions.SaveNote -> saveNote()
                is UpsertNoteActions.UpdateNoteUiContent -> updateNoteUiContent(noteContent = upsertNoteActions.noteContent)
                is UpsertNoteActions.UpdateNoteUiTitle -> updateNoteUiTitle(noteTitle = upsertNoteActions.noteTitle)
                UpsertNoteActions.OnDialogDismiss -> onDialogCancel()
                UpsertNoteActions.OnSaveNoteClick -> onDialogShow(
                    titleResId = R.string.dialog_save_title,
                    bodyResId = R.string.dialog_save_body_text,
                    confirmButtonId = R.string.save,
                    dismissButtonId = R.string.cancel,
                    isSaveNote = true,
                )
                UpsertNoteActions.OnCloseIconClick -> onDialogShow(
                    titleResId = R.string.dialog_discard_title,
                    bodyResId = R.string.dialog_discard_body_text,
                    confirmButtonId = R.string.discard,
                    dismissButtonId = R.string.keep_editing,
                    isSaveNote = false,
                )
            }
        }
    }


    private suspend fun close() {
        showLoader(showLoader = true)
        onDialogCancel()
        delay(100)
        showLoader(showLoader = false)

        _events.send(UpsertNoteEvents.Close)
    }

    private fun showLoader(showLoader: Boolean) {
        _state.update { newState ->
            newState.copy(
                isLoading = showLoader
            )
        }
    }

    private fun onDialogShow(
        @StringRes titleResId: Int,
        @StringRes bodyResId: Int,
        @StringRes confirmButtonId: Int,
        @StringRes dismissButtonId: Int,
        isSaveNote: Boolean,
    ) {
        _state.update { newState ->
            newState.copy(
                titleResId = titleResId,
                bodyResId = bodyResId,
                confirmButtonId = confirmButtonId,
                dismissButtonId = dismissButtonId,
                isSaveNote = isSaveNote
            )
        }
        showDialog(showDialog = true)
    }

    private fun onDialogCancel() {
        showDialog(showDialog = false)
    }

    private fun showDialog(showDialog: Boolean) {
        _state.update { newState ->
            newState.copy(
                showDialog = showDialog
            )
        }
    }

    private suspend fun saveNote() {
        showLoader(showLoader = true)
        val now: ZonedDateTime = ZonedDateTime.now()
        val isEdit = _state.value.isEdit
        Timber.tag("MyTag").d("noteBefore: ${_state.value.noteUi?.toNote()}")
        _state.update { newState ->
            newState.copy(
                noteUi = if (isEdit) newState.noteUi!!.copy(
                    lastEditedAt = now,
                ) else {
                    newState.noteUi!!.copy(
                        createdAt = now,
                        lastEditedAt = now
                    )
                }
            )
        }
        val note = _state.value.noteUi!!.toNote()
        Timber.tag("MyTag").d("note: $note")
        Timber.tag("MyTag").d("content length: ${note.content.length}")
        val saveNoteResult = noteRepository.upsertNote(note = note)
        Timber.tag("MyTag").d("saveNoteResult ${saveNoteResult.asEmptyDataResult()}")
        showLoader(showLoader = false)
        showDialog(showDialog = false)
        delay(100)
        onDialogCancel()
        when (saveNoteResult) {
            is Result.Error -> Timber.tag("MyTag")
                .e("upsertNoteViewModel: saveNote: ${saveNoteResult.error}")

            is Result.Success -> {
                _events.send(UpsertNoteEvents.Close)
            }
        }
    }

    private fun updateNoteUiTitle(noteTitle: String) {
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