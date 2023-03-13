package com.example.readingnotesapp.feature_note.domain.use_case


import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.repository.NoteRepository


class GetNote (
    private val repository: NoteRepository
){

    suspend operator fun invoke(id: Int): Note?{
        return repository.getNoteById(id)
    }

}