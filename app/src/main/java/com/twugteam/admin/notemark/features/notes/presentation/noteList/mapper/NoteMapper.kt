package com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.presentation.ui.formatAsNoteDate
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import java.time.Instant
import java.time.ZoneId

fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt.formatAsNoteDate(),
        lastEditedAt = lastEditedAt.formatAsNoteDate()
    )
}

fun NoteUi.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(lastEditedAt).atZone(ZoneId.of("UTC"))
    )
}