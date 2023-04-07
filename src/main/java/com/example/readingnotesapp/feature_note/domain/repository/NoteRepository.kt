package com.example.readingnotesapp.feature_note.domain.repository

import com.example.readingnotesapp.feature_note.data.model.Note
import kotlinx.coroutines.flow.Flow


///Why is this interface?
//it is because it just good for testing because we can easily create fake versions
//of this repository.
//for test cases we often just like to have fakes because we don't want to use our real api
//in database to run test cases.
interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}