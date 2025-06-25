package com.twugteam.admin.notemark.features.notes.presentation.noteList.model

import java.time.ZonedDateTime

data class NoteUi(
    val id: String?,
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val lastEditedAt: ZonedDateTime
)
