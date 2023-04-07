package com.example.readingnotesapp.feature_note.domain.use_case

import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}