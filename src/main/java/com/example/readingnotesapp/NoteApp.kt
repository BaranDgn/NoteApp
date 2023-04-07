package com.example.readingnotesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//Adding dependecy injection
//This is the first step
//the go to manifest to register this application class in manifest

@HiltAndroidApp
class NoteApp : Application()