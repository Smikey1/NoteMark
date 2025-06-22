package com.twugteam.admin.notemark.features.note.presentation.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.twugteam.admin.notemark.core.domain.note.Note
import com.twugteam.admin.notemark.core.domain.util.UUID
import com.twugteam.admin.notemark.features.note.presentation.model.NoteUi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Note.toNoteUi(): NoteUi {
    return NoteUi(
        id = UUID.toString(uuid = id),
        title = title,
        content = content,
        createdAt = zonedDateTimeToString(createdAt),
        lastEditedAt = zonedDateTimeToString(lastEditedAt)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun zonedDateTimeToString(zonedDateTime: ZonedDateTime): String {
    val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
    val formatterDateTime = DateTimeFormatter.ofPattern("dd MMM yyyy").format(localDateTime)
    return formatterDateTime
}