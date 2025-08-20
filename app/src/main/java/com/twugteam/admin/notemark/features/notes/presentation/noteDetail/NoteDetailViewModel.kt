package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.domain.util.Result
import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.core.presentation.ui.UiText.*
import com.twugteam.admin.notemark.core.presentation.ui.toNote
import com.twugteam.admin.notemark.core.presentation.ui.toNoteUi
import com.twugteam.admin.notemark.features.notes.domain.NoteRepository
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import kotlinx.coroutines.Job
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
import kotlin.time.Duration.Companion.seconds

class NoteDetailViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state: MutableStateFlow<NoteDetailUiState> =
        MutableStateFlow(NoteDetailUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<NoteDetailEvents>()
    val events = _events.receiveAsFlow()


    private var job: Job? = null

    init {
        val noteId: String? = savedStateHandle.get<String>("noteId")
        viewModelScope.launch {
            getNoteById(noteId = noteId)

        }
    }

    private suspend fun getNoteById(noteId: String?) {
        //navigated from floating button (Add Note)
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
                    mode = Mode(
                        isEdit = true,
                        isAdd = true,
                        isView = false,
                        isReader = false
                    ),
                )
            }
            return
        }
        //Clicked on a Note
        val note = noteRepository.getNoteById(id = noteId)
        _state.update { newState ->
            newState.copy(
                noteUi = note.toNoteUi(),
            )
        }
        //show in viewMode
        setViewMode()
    }


    fun onActions(noteDetailActions: NoteDetailActions) {
        when (noteDetailActions) {
            NoteDetailActions.Close -> close()
            is NoteDetailActions.SaveNoteDetail -> saveNote()
            is NoteDetailActions.UpdateNoteDetailUiContent -> updateNoteUiContent(noteContent = noteDetailActions.noteContent)
            is NoteDetailActions.UpdateNoteDetailUiTitle -> updateNoteUiTitle(noteTitle = noteDetailActions.noteTitle)
            NoteDetailActions.OnDialogDismiss -> onDialogCancel()
            NoteDetailActions.OnSaveNoteDetailClick -> onDialogShow(
                titleResId = StringResource(R.string.dialog_save_title),
                bodyResId = StringResource(R.string.dialog_save_body_text),
                confirmButtonId = StringResource(R.string.save),
                dismissButtonId = StringResource(R.string.cancel),
                isSaveNote = true,
            )

            NoteDetailActions.OnCloseIconClick -> onDialogShow(
                titleResId = StringResource(R.string.dialog_discard_title),
                bodyResId = StringResource(R.string.dialog_discard_body_text),
                confirmButtonId = StringResource(R.string.discard),
                dismissButtonId = StringResource(R.string.keep_editing),
                isSaveNote = false,
            )

            NoteDetailActions.EditMode -> setEditMode()
            NoteDetailActions.ViewMode -> setViewMode()
            NoteDetailActions.ReaderMode -> setReaderMode()

            is NoteDetailActions.SetReadModeActivate -> setReadModeActivate(isReadModeActivate = noteDetailActions.isReadModeActivate)

            is NoteDetailActions.OnScreenTap -> onScreenTap()
            is NoteDetailActions.ShowSelectedBottomBar -> showToast(selectedMode = noteDetailActions.selectedMode)
        }
    }

    private fun showToast(selectedMode: UiText){
        viewModelScope.launch {
            _events.send(NoteDetailEvents.ShowToast(selectedMode = selectedMode))
        }
    }

    private fun onScreenTap() {
        val isReadModeActivate = _state.value.isReadModeActivate
        Timber.tag("MyTag").d("isReadModeActivate: $isReadModeActivate")
        //cancel job (stop the 5sec delay)
        job?.cancel()
        if(isReadModeActivate){
            setReadModeActivate(isReadModeActivate = false)
            Timber.tag("MyTag").d("Returning early")
            return
        }
        Timber.tag("MyTag").d("Starting job")
        job = viewModelScope.launch {
            setReadModeActivate(true)
            delay(5.seconds)
            setReadModeActivate(false)
        }
    }

    private fun setReadModeActivate(isReadModeActivate: Boolean){
        //cancel job if exist
        job?.cancel()
        _state.update { newState ->
            newState.copy(isReadModeActivate = isReadModeActivate)
        }
    }

    private fun setEditMode() {
        _state.update { newState ->
            newState.copy(
                mode = newState.mode.copy(
                    isEdit = true,
                    isView = false,
                    isAdd = false,
                    isReader = false
                ),
            )
        }
    }

    private fun setViewMode() {
        _state.update { newState ->
            newState.copy(
                mode = newState.mode.copy(
                    isView = true,
                    isReader = false,
                    isEdit = false,
                    isAdd = false
                ),
            )
        }
    }

    private fun setReaderMode() {
        val readerMode = _state.value.mode.isReader
        if(readerMode){
            setViewMode()
            return
        }
        _state.update { newState ->
            newState.copy(
                mode = newState.mode.copy(
                    isReader = true,
                    isView = false,
                    isEdit = false,
                    isAdd = false
                ),
            )
        }

        //show AllNotes and Extended Fab for 5.seconds
        onScreenTap()
    }


    private fun close() {
        viewModelScope.launch {
            showLoader(showLoader = true)
            onDialogCancel()
            //show loader little before closing
            delay(100)
            showLoader(showLoader = false)

            _events.send(NoteDetailEvents.Close)
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
        titleResId: UiText,
        bodyResId: UiText,
        confirmButtonId: UiText,
        dismissButtonId: UiText,
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

            //isAdding or updating existing note
            val isAdd = _state.value.mode.isAdd
            Timber.tag("MyTag").d("isAdd: $isAdd")
            _state.update { newState ->
                newState.copy(
                    // !isAdd updating existing note
                    noteUi = if (!isAdd) newState.noteUi!!.copy(
                        lastEditedAt = now,
                    ) else {
                        //isAdd creating new note
                        newState.noteUi!!.copy(
                            createdAt = now,
                            lastEditedAt = now
                        )
                    }
                )
            }
            val note = _state.value.noteUi!!.toNote()
            Timber.tag("MyTag").d("newNote : ${note.title}")
            //isAdd will check if we are adding new note
            //or updating existing note
            val saveNoteResult = noteRepository.upsertNote(note = note, isAdd = isAdd)
            showLoader(showLoader = false)
            onDialogCancel()
            when (saveNoteResult) {
                is Result.Error -> Timber.tag("MyTag")
                    .e("upsertNoteViewModel: saveNote: error: ${saveNoteResult.error}")

                is Result.Success -> {
                    Timber.tag("MyTag")
                        .d("upsertNoteViewModel: saveNote: success: ${saveNoteResult.data}")

                    if(!isAdd) {
                        //set view mode if the new note is saved
                        setViewMode()
                    }else{
                        _events.send(NoteDetailEvents.NavigateToNoteList)
                    }
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