package com.twugteam.admin.notemark.core.database.sync

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.twugteam.admin.notemark.core.database.converters.NoteDtoTypeConverter
import com.twugteam.admin.notemark.core.database.converters.SyncOperationConverter
import com.twugteam.admin.notemark.features.notes.data.model.NoteDto

@Keep
@Entity(tableName = "SyncEntity")
@TypeConverters(SyncOperationConverter::class, NoteDtoTypeConverter::class)
data class SyncEntity(
    //since noteId is UUID we can use it here
    //using upsert will update or insert depending on this noteId
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("noteId") val noteId: String,
    @ColumnInfo("userId") val userId: String,
    @ColumnInfo("operation") val operation: SyncOperation,
    @ColumnInfo("payload") val payload: NoteDto?,
    @ColumnInfo("timeStamp") val timeStamp: String?,
)
