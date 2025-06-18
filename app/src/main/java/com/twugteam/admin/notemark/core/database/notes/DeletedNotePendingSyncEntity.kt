package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedNotePendingSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val noteId: String,
    val username: String
)
