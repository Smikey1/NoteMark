package com.twugteam.admin.notemark.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {

    //auth navGraph
    @Serializable
    data object AuthGraph : Screens

    //auth screens
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

}
