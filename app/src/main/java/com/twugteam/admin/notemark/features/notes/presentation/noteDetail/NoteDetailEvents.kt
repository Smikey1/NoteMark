package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

sealed interface NoteDetailEvents {
    data object Close : NoteDetailEvents
}