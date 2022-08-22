package com.gharibe.smartapps.presentaion.notes

import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.util.NoteOrder

sealed class NotesEvents{
    data class Order(val noteOrder: NoteOrder):NotesEvents()
    data class DeleteNote(val note: Note):NotesEvents()
    object RestoreNote :NotesEvents()
    object ToggleOrderSection : NotesEvents()
}
