package com.twugteam.admin.notemark.features.notes.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SyncOperationResponse(
    val notes: List<NoteDto>,
    val total: Int
)