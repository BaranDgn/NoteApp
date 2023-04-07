package com.example.readingnotesapp.feature_note.presentation.notes

import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.util.NoteOrder
import com.example.readingnotesapp.feature_note.domain.util.OrderType

//want to have a state that actually decides if this order section is
//visible or not.
data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
