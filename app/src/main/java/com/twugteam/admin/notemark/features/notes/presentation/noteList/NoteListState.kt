package com.twugteam.admin.notemark.features.notes.presentation.noteList

import com.twugteam.admin.notemark.core.domain.notes.Note
import com.twugteam.admin.notemark.features.notes.presentation.noteList.mapper.toNoteUi
import com.twugteam.admin.notemark.features.notes.presentation.noteList.model.NoteUi
import java.time.ZonedDateTime

data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
//    val notes: List<NoteUi> = listOf(
//        Note(
//            id = "1",
//            title = "Title",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        ),
//        Note(
//            id = "2",
//            title = "Title of the note",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue." +
//                    "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi s",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        ),
//        Note(
//            id = "3",
//            title = "Title",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        ),
//        Note(
//            id = "4",
//            title = "Title of the note",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        ),
//        Note(
//            id = "5",
//            title = "Title",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        ),
//        Note(
//            id = "6",
//            title = "Title of the note",
//            content = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue.",
//            createdAt = ZonedDateTime.now(),
//            lastEditedAt = ZonedDateTime.now()
//        )
//    ).map {
//        it.toNoteUi()
//    }
)
