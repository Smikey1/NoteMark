package com.twugteam.admin.notemark.features.notes.data.model

import java.util.concurrent.TimeUnit

data class SyncInterval(
    val interval: Long? = null, val text: String = "Manual only",
    val timeUnit: TimeUnit? = null,
)