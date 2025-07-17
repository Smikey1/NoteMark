package com.twugteam.admin.notemark.features.notes.presentation.noteDetail

import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi

data class NoteDetailUiState(
    val noteUi: NoteUi? = null,
    val originalNote: NoteUi? = null,
    val mode: Mode = Mode(),
    val isReadModeActivate: Boolean = false,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val titleResId: Int = R.string.dialog_save_title,
    val bodyResId: Int = R.string.dialog_save_body_text,
    val confirmButtonId: Int = R.string.save,
    val dismissButtonId: Int = R.string.cancel,
    val isSaveNote: Boolean = true,
)

data class Mode(
    val isView: Boolean = false,
    val isReader: Boolean = false,
    val isEdit: Boolean = false,
    val isAdd: Boolean = false
)