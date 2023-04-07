package com.example.readingnotesapp.di

import android.app.Application
import androidx.room.Room
import com.example.readingnotesapp.feature_note.data.DataSources.NoteDatabase
import com.example.readingnotesapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.readingnotesapp.feature_note.domain.repository.NoteRepository
import com.example.readingnotesapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//3 step is to open a object module to put all the dependencies.
//this class is to be able to inject dependencies
//to provide these in a called module

//We put all the dependecies here.

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //this will require our application context to create that,
    //so we passed the application.
    //Dagger hilt will automatically insert that
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCase{
        return NoteUseCase(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

}