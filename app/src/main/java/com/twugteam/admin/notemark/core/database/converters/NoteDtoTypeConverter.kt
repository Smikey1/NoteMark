package com.twugteam.admin.notemark.core.database.converters

import androidx.room.TypeConverter
import com.twugteam.admin.notemark.features.notes.data.model.NoteDto
import kotlinx.serialization.json.Json

class NoteDtoTypeConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromNoteDto(note: NoteDto): String {
        return json.encodeToString(NoteDto.serializer(), note)
    }

    @TypeConverter
    fun toNoteDto(data: String): NoteDto {
        return json.decodeFromString(NoteDto.serializer(), data)
    }
}