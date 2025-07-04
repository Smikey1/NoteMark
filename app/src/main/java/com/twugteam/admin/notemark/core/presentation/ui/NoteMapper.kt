package com.twugteam.admin.notemark.core.presentation.ui

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}

fun NoteUi.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}