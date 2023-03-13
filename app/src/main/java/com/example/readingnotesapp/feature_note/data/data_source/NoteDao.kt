package com.example.readingnotesapp.feature_note.data.data_source

import androidx.room.*
import com.example.readingnotesapp.feature_note.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    //To get all the notes of database
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    //give a note by id
    //this is a suspend function because we directly return note.
    //don't wrap this around the flow object or something.
    @Query("SELECT * FROM note WHERE id = :id")
   suspend fun getNoteById(id : Int) : Note?

    //onConflictStrategy.Replace --> if we call this insert function with an id that already exists in database
   //it will just update the existing entry.
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNote(note: Note)

   @Delete
   suspend fun deleteNote(note: Note)

}