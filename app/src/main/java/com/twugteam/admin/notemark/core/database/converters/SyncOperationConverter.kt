package com.twugteam.admin.notemark.core.database.converters

import androidx.room.TypeConverter
import com.twugteam.admin.notemark.core.database.sync.SyncOperation

class SyncOperationConverter {
    @TypeConverter
    fun fromOperation(value: SyncOperation): String = value.name

    @TypeConverter
    fun toOperation(value: String): SyncOperation = SyncOperation.valueOf(value)
}