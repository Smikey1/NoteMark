package com.twugteam.admin.notemark.features.notes.presentation.noteList.model

import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import java.time.ZonedDateTime

fun NoteEntity.toNoteUi(): NoteUi {
    return NoteUi(
        id = id,
        title = title,
        content = content,
        createdAt = ZonedDateTime.parse(createdAt),
        lastEditedAt = ZonedDateTime.parse(lastEditedAt)
    )
}