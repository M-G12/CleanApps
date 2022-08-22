package com.gharibe.smartapps.presentaion.util

sealed class Screen (val route : String) {
    object NoteScreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_notes_screen")

}
