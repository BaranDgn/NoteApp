package com.example.readingnotesapp.feature_note.presentation.notes

import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.util.NoteOrder

//We define everything we can do on the main screen here.
//like ordering the notes, deleting the note, radiobuttons click, restore note
sealed class NoteEvent{
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    object RestoreNote: NoteEvent()
    object ToogleOrderSection: NoteEvent()
}
