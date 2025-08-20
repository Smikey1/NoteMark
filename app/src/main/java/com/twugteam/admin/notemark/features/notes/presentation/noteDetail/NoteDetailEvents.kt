package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

import com.twugteam.admin.notemark.core.presentation.ui.UiText

sealed interface NoteDetailEvents {
    data object Close : NoteDetailEvents
    data object NavigateToNoteList: NoteDetailEvents
    data class ShowToast(val selectedMode: UiText): NoteDetailEvents
}