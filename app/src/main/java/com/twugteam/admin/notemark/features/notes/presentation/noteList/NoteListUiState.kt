package com.twugteam.admin.notemark.features.notes.presentation.noteList

import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

data class NoteListUiState(
    val notes: List<NoteUi> = emptyList(),
    val username: String = "PL",
    val showDialog: Boolean = false,
    val noteToDeleteId: String? = null,
    val isLoading: Boolean = false,
)