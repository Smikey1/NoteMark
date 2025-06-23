package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

data class UpsertNoteState(
    val noteUi: NoteUi? = null,
    val isEdit: Boolean = false,
)
