package com.twugteam.admin.notemark.features.notes.presentation.noteList

sealed interface NoteListActions {
    data class NavigateToNoteDetail(val noteId: String?) : NoteListActions
    data class OnNoteDelete(val noteId: String) : NoteListActions
    data object OnDialogConfirm : NoteListActions
    data object OnDialogDismiss : NoteListActions
    data object NavigateToSettings: NoteListActions
}