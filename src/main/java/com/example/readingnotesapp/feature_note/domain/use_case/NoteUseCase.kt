package com.example.readingnotesapp.feature_note.domain.use_case


//wrapping uses cases for a single feature into one class
//so we just need to inject that single class that contains
//all of our use cases for that feature into viewmodel constructor
//and that makes it much cleaner.

//This will be the class inject into view model.
data class NoteUseCase(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote:GetNote
)
