package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.presentation.ui.toNote
import com.twugteam.admin.notemark.core.presentation.ui.toNoteUi
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime

class UpsertNoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state: MutableStateFlow<UpsertNoteUiState> =
        MutableStateFlow(UpsertNoteUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<UpsertNoteEvents>()
    val events = _events.receiveAsFlow()

    init {
        val noteId: String? = savedStateHandle.get<String>("noteId")
        viewModelScope.launch {
            getNoteById(noteId = noteId)
        }
    }

    private suspend fun getNoteById(noteId: String?) {
        if (noteId.isNullOrEmpty()) {
            val now: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
            _state.update { newState ->
                newState.copy(
                    noteUi = NoteUi(
                        id = null,
                        title = "",
                        content = "",
                        createdAt = now,
                        lastEditedAt = now
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
    }


    fun onActions(upsertNoteActions: UpsertNoteActions) {
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


    private fun close() {
        viewModelScope.launch {
            showLoader(showLoader = true)
            onDialogCancel()
            //show loader little before closing
            delay(100)
            showLoader(showLoader = false)

            _events.send(UpsertNoteEvents.Close)
        }
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

    private fun saveNote() {
        viewModelScope.launch {
            showLoader(showLoader = true)
            val now: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
            val isEdit = _state.value.isEdit
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
            Timber.tag("MyTag").d("newNote : $note")
            val saveNoteResult = noteRepository.upsertNote(note = note, isEditing = isEdit)
            showLoader(showLoader = false)
            onDialogCancel()
            when (saveNoteResult) {
                is Result.Error -> Timber.tag("MyTag")
                    .e("upsertNoteViewModel: saveNote: error: ${saveNoteResult.error}")

                is Result.Success -> {
                    Timber.tag("MyTag")
                        .d("upsertNoteViewModel: saveNote: success: ${saveNoteResult.data}")
                    _events.send(UpsertNoteEvents.Close)
                }
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