package com.example.readingnotesapp.feature_note.data.repository

import com.example.readingnotesapp.feature_note.data.DataSources.NoteDao
import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository{

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }
}

//use case : contain business logic, so what previously with plain MVVM did in the viewmodel is now done use cases.
//use cases make the more readable, it is a single user action like getting note, adding a new note. It is a single thing user do.
//they quickly reveal, we can know what They contain by looking its name.
//they make the code very reusable.