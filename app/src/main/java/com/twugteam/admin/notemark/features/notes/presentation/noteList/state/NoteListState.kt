package com.twugteam.admin.notemark.features.notes.presentation.noteList.state

import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

//TODO: In this architecture pattern, no need to put state data class inside the package
data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
    val username: String = "PL",
    val showDialog: Boolean = false,
    val noteToDeleteId: String? = null,
    val isLoading: Boolean = false,
)