package com.example.readingnotesapp.feature_note.presentation.AddEditNote

import androidx.compose.ui.focus.FocusState


//this will contain some events basically for every single UI action user can make
//it is the same as in our notes screen.

sealed class AddEditNoteEvent {
    //when we enter title or content, we will have another event that is hide the hint. So we must specify another event for this.(FocusTitle)
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()

    data class ChangeColor(val color: Int): AddEditNoteEvent()

    object SaveNote: AddEditNoteEvent()
}

