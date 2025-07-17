package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

sealed interface NoteDetailActions {
    data class UpdateNoteDetailUiTitle(val noteTitle: String) : NoteDetailActions
    data class UpdateNoteDetailUiContent(val noteContent: String) : NoteDetailActions
    data class SaveNoteDetail(val noteUi: NoteUi) : NoteDetailActions
    data object OnDialogDismiss : NoteDetailActions
    data object OnSaveNoteDetailClick : NoteDetailActions
    data object OnCloseIconClick : NoteDetailActions
    data object Close : NoteDetailActions
    data object EditMode: NoteDetailActions
    data object ViewMode: NoteDetailActions
    data object ReaderMode: NoteDetailActions
    data object OnScreenTap: NoteDetailActions
    data class SetReadModeActivate(val isReadModeActivate: Boolean): NoteDetailActions
}