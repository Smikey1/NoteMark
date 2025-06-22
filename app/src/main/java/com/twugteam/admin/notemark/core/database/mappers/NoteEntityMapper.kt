package com.twugteam.admin.notemark.core.database.mappers

import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.core.domain.util.UUID
import java.time.Instant
import java.time.ZoneId

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: UUID.new().toString(),
        title = title,
        content = title,
        createdAt = createdAt.toInstant().toString(),
        lastEditedAt = lastEditedAt.toInstant().toString(),
    )
}


fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
    )
}