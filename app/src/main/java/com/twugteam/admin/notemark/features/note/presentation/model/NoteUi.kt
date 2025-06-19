package com.twugteam.admin.notemark.features.note.presentation.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class NoteUi(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val lastEditedAt: String
){
    @RequiresApi(Build.VERSION_CODES.O)
    val currentYear = LocalDate.now().year
    val createdYear = createdAt.split(" ").takeLast(1)

}
