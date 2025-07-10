package com.twugteam.admin.notemark.features.notes.presentation.noteList

sealed interface NoteListEvents {
    data class NavigateToUpsertNote(val noteId: String?) : NoteListEvents
    data object NavigateToSettings: NoteListEvents
}