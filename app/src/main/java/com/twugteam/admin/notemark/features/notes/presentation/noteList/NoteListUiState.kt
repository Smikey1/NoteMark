package com.twugteam.admin.notemark.features.notes.presentation.noteList

import androidx.paging.PagingData
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class NoteListUiState(
    val notesPagingFlow: Flow<PagingData<NoteUi>> = emptyFlow(),
    val isLoading: Boolean = true,
    val notes: List<NoteUi> = emptyList(),
    val username: String = "PL",
    val showDialog: Boolean = false,
    val noteToDeleteId: String? = null,
    val dialogLoading: Boolean = false,
    val isConnected: Boolean = false,
)