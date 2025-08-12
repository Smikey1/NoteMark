package com.twugteam.admin.notemark.features.notes.presentation.settings

import com.twugteam.admin.notemark.core.presentation.ui.UiText
import com.twugteam.admin.notemark.features.notes.constant.Constants
import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval

data class SettingsUiState(
    val showSnackBar: Boolean = false,
    val snackBarText: UiText = UiText.DynamicString(""),
    val selectedSyncingInterval: SyncInterval = Constants.syncingIntervalList.first(),
    val syncingIntervalList: List<SyncInterval> = Constants.syncingIntervalList,
    val isSyncExpanded: Boolean = false,
    val lastSyncTimestamp: Long = -1L,
    val showDialog: Boolean = false,
    val isLoading: Boolean = false,
)