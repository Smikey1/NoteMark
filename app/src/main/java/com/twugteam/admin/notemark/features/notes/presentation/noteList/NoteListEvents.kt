package com.twugteam.admin.notemark.features.notes.presentation.noteList

sealed interface NoteListEvents {
    data class NavigateToNoteDetail(val noteId: String?) : NoteListEvents
    data object NavigateToSettings: NoteListEvents
}