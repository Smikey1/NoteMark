package com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.presentation.ui.formatAsNoteDate
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt.formatAsNoteDate(),
        lastEditedAt = lastEditedAt.formatAsNoteDate()
    )
}