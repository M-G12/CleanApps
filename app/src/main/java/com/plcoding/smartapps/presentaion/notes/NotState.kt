package com.gharibe.smartapps.presentaion.notes

import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.feature_note.domain.util.NoteOrder
import com.gharibe.smartapps.feature_note.domain.util.OrderType

data class NotState(
    val notes : List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false,
)
