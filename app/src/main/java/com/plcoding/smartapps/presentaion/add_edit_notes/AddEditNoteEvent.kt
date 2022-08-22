package com.gharibe.smartapps.presentaion.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitle (val value:String):AddEditNoteEvent()
    data class ChangeTitleFocus (val focusState: FocusState):AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus (val focusState: FocusState):AddEditNoteEvent()
    data class ChangeColor (val newColor: Int):AddEditNoteEvent()
    object SaveNote :AddEditNoteEvent()

}