package com.twugteam.admin.notemark.core.database.notes

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

//keep is used to avoid pro-guard changing fields at release build
//and to ensure safety we used tableName and columnInfo
@Keep
@Entity(tableName = "NoteEntity")
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content")val content: String,
    @ColumnInfo(name = "createdAt") val createdAt: String,
    @ColumnInfo(name = "lastEditedAt") val lastEditedAt: String
)
