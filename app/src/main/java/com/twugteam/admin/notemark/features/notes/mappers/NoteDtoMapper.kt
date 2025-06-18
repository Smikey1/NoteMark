package com.twugteam.admin.notemark.features.notes.mappers

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.features.notes.data.CreateNoteRequest
import com.twugteam.admin.notemark.features.notes.data.NoteDto
import java.time.Instant
import java.time.ZoneId

fun NoteDto.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
    )
}

fun Note.toCreateNoteRequest(): CreateNoteRequest {
    return CreateNoteRequest(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt.toInstant().toString(),
        lastEditedAt = lastEditedAt.toInstant().toString()
    )
}
