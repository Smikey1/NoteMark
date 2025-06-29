package com.twugteam.admin.notemark.core.presentation.ui

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.getInitial(): String {
    val trimmed = this.trim()
    val words = trimmed.split(' ').filter { it.isNotEmpty() }

    return when {
        words.isEmpty() -> ""
        words.size == 1 -> words[0].take(2).uppercase()
        else -> {
            val firstInitial = words.first().first()
            val lastInitial = words.last().first()
            "$firstInitial$lastInitial".uppercase()
        }
    }
}

fun ZonedDateTime.formatToString(): String {
    val localDateTime = this.withZoneSameInstant(ZoneId.systemDefault())
    return DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH).format(localDateTime)
}

fun ZonedDateTime.formatAsNoteDate(): String {
    val localDate = this.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate()
    val currentYear = ZonedDateTime.now(ZoneId.systemDefault()).year

    val formatter = if (localDate.year == currentYear) {
        DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)
    } else {
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    }
    return formatter.format(localDate).toUpperCase(Locale.ENGLISH)
}