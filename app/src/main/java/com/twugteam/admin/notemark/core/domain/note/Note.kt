package com.twugteam.admin.notemark.core.domain.note

import java.time.ZonedDateTime
import java.util.UUID

data class Note(
    val id: UUID = com.twugteam.admin.notemark.core.domain.util.UUID.new(),
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val lastEditedAt: ZonedDateTime
)