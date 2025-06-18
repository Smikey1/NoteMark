package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CreatedNotePendingSyncEntity(
    @Embedded val noteEntity: NoteEntity,
    @PrimaryKey(autoGenerate = false)
    val noteId: String = noteEntity.id,
    val username: String
)
