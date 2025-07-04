package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

sealed interface UpsertNoteActions {
    data class UpdateNoteUiTitle(val noteTitle: String) : UpsertNoteActions
    data class UpdateNoteUiContent(val noteContent: String) : UpsertNoteActions
    data class SaveNote(val noteUi: NoteUi) : UpsertNoteActions
    data object OnDialogDismiss : UpsertNoteActions
    data object OnSaveNoteClick : UpsertNoteActions
    data object OnCloseIconClick : UpsertNoteActions
    data object Close : UpsertNoteActions
}