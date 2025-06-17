package com.twugteam.admin.notemark.core.database.notes

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}