package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

sealed interface UpsertNoteEvents {
    data object Close : UpsertNoteEvents
}