package com.twugteam.admin.notemark.features.notes.presentation.noteList.util

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