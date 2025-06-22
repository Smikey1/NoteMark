package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twugteam.admin.notemark.core.domain.util.UUID

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.new().toString(),
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)
