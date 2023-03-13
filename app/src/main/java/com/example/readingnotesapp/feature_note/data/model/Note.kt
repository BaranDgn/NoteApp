package com.example.readingnotesapp.feature_note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.readingnotesapp.ui.theme.*

@Entity
data class Note(
    val title : String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    //hard coded list that contains colors
    companion object{
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink)
    }
}

//for checking if the title is blank or not.
class InvalidNoteException(message: String): Exception(message)