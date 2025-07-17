package com.twugteam.admin.notemark.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {

    //auth navGraph
    @Serializable
    data object AuthGraph : Screens

    @Serializable
    data object Landing : Screens

    @Serializable
    data object LogIn : Screens

    @Serializable
    data object Register : Screens



    //noteMark navGraph
    @Serializable
    data object NoteGraph : Screens

    //noteMark screens
    @Serializable
    data object NoteList: Screens

    @Serializable
    data class NoteDetail(val noteId: String?): Screens

    @Serializable
    data object Settings: Screens

}
