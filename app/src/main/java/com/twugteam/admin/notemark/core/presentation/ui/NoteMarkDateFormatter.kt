package com.twugteam.admin.notemark.core.presentation.ui

import com.twugteam.admin.notemark.R
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import java.util.*


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
fun formatToViewMode(dateTime: ZonedDateTime): UiText {
    val now = ZonedDateTime.now(dateTime.zone)
    val duration = Duration.between(dateTime, now)

    return if (duration.toMinutes() < 5) {
        UiText.StringResource(R.string.just_now)
    } else {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        UiText.DynamicString(dateTime.format(formatter))
    }
}

fun formatToUiText(timeStamp: Long): UiText {
    if(timeStamp == -1L){
        return UiText.StringResource(R.string.never_synced)
    }

    //get current date
    val now = System.currentTimeMillis()
    //difference between long variable and the current date(now)
    val diff = now - timeStamp

    return when {
        diff < TimeUnit.MINUTES.toMillis(5) -> {
            UiText.StringResource(R.string.just_now)
        }

        diff < TimeUnit.DAYS.toMillis(7) -> {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
            val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
            val days = TimeUnit.MILLISECONDS.toDays(diff)

            val parts = mutableListOf<String>()
            if (days > 0) {
                parts.add("$days day${if (days != 1L) "s" else ""}")
            }
            if (hours > 0) {
                parts.add("$hours hour${if (hours != 1L) "s" else ""}")
            }
            if (minutes > 0) {
                parts.add("$minutes minute${if (minutes != 1L) "s" else ""}")
            }

            UiText.DynamicString(parts.joinToString(" ") + " ago")
        }

        else -> {
            val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            UiText.DynamicString(formatter.format(Date(timeStamp)))
        }
    }
}


