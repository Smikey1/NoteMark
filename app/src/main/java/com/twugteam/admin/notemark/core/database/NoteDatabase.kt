package com.twugteam.admin.notemark.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.twugteam.admin.notemark.core.database.converters.NoteDtoTypeConverter
import com.twugteam.admin.notemark.core.database.converters.SyncOperationConverter
import com.twugteam.admin.notemark.core.database.notes.NoteDao
import com.twugteam.admin.notemark.core.database.notes.NoteEntity
import com.twugteam.admin.notemark.core.database.sync.SyncDao
import com.twugteam.admin.notemark.core.database.sync.SyncEntity

@Database(
    entities = [
        NoteEntity::class,
        SyncEntity::class
    ],
    version = 1
)
@TypeConverters(SyncOperationConverter::class, NoteDtoTypeConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val syncDao: SyncDao
}