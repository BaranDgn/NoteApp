package com.example.readingnotesapp.feature_note.presentation.AddEditNote

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class ShareNote(

) {

    fun sharingNote(){
        var sentIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "test text")
            this.putExtra(Intent.EXTRA_TEXT, "test text")
            this.type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sentIntent, null)
       // startActivity(shareIntent)
    }

}