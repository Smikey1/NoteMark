package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)
