package com.twugteam.admin.notemark.features.notes.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.features.notes.data.CreateNoteRequest
import com.twugteam.admin.notemark.features.notes.data.NoteDto
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun NoteDto.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
        lastEditedAt = Instant.parse(createdAt).atZone(ZoneId.of("UTC")),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Note.toCreateNoteRequest(): CreateNoteRequest {
    return CreateNoteRequest(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt.toInstant().toString(),
        lastEditedAt = lastEditedAt.toInstant().toString()
    )
}
