package com.twugteam.admin.notemark.features.notes.data.model

sealed class SyncResult(){
    data object Success: SyncResult()
    data class Failed(val failedMessage: String): SyncResult()
    data class Partial(val errorListMessage: List<String>): SyncResult()
}