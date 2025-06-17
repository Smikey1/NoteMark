package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "1", //TODO Update the uuid value
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
)
