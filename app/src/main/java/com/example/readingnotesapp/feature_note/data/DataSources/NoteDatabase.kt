package com.example.readingnotesapp.feature_note.data.DataSources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.readingnotesapp.feature_note.data.model.Note

//entities --> define the table we have in database
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){

    abstract val noteDao: NoteDao

    //define database name here
    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}

//repository --> directly access our data sources so either
//data base or API, usually be multiple. and repository directly accesses these
//job of the repository to take these data sources, and
//determine which one to actually forward to corresponding
//use cases.
// for example:
//if you get an api and you also have caching layer and mechanism
//in your app. Then the repository needs to decide
//do I now load the data from the cache or do i now load the data from api.

//so this decision logic and checking if there were any errors in that api
//request or reading from database.
//and then just forwarding this data to the use case that should not know where the repository gets the data from
//just want to get the data. That is what use cases care about.