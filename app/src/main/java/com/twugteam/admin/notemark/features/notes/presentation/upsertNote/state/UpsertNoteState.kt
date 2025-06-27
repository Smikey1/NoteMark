package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.state

import androidx.annotation.StringRes
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

data class UpsertNoteState(
    val noteUi: NoteUi? = null,
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    @StringRes val titleResId: Int = R.string.dialog_save_title,
    @StringRes val bodyResId: Int = R.string.dialog_save_body_text,
    @StringRes val confirmButtonId: Int = R.string.save,
    @StringRes val dismissButtonId: Int = R.string.cancel,
    val isSaveNote: Boolean = true,
)