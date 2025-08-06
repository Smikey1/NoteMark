package com.twugteam.admin.notemark.core.presentation.ui

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    return formatter.format(localDate).uppercase(Locale.ENGLISH)
}

//convert ZonedDateTime into String
fun formatToViewMode(dateTime: ZonedDateTime): String {
    val now = ZonedDateTime.now(dateTime.zone)
    val duration = Duration.between(dateTime, now)

    return if (duration.toMinutes() < 5) {
        "Just now"
    } else {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        dateTime.format(formatter)
    }
}

