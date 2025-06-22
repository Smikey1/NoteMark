package com.twugteam.admin.notemark.core.domain.notes

import java.time.ZonedDateTime

data class Note(
    val id: String?,
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val lastEditedAt: ZonedDateTime
)