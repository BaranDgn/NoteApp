package com.example.readingnotesapp.feature_note.presentation.add_edit_note


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingnotesapp.feature_note.data.model.InvalidNoteException
import com.example.readingnotesapp.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.readingnotesapp.feature_note.data.model.Note
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch



@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle //it is kind a bundle that contains these navigation arguments, so we don't need to apss that as a parameter to the view model to function or something.
    //we can just acccess this with savve state, it automatically injects this.
):ViewModel(){

    //not wrapping this interesting into a single state object
    //because we text fields and then that's not so optimal
    //caused that whenever we type a character here,
    //then this text field recomposes and if we put this in a single
    //state class that combine multiole states
    //if we enter one key, text field will recompose the whole UI
    //That is why we have seperate states :
    private val _notetitle = mutableStateOf(NoteTextFieldState(
        //assign default hint
        hint = "Enter Title..."
    ))
    val noteTitle : State<NoteTextFieldState> = _notetitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent : State<NoteTextFieldState> = _noteContent

    //toArgb() --> to get the color as a integer.
    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    //what is this evenflow for?
    //if we use these normal compose states, They hold state.
    //it is one time event that don't represent state.
    //for example, we want to show a snackbar, then we will only wabn to show that snake bow once.
    //we don't show that again when we actually rotate the screen so it's not really state. This is why we used it.
    //we can send one-time events from the viewmodel in this shared flow.
    //then observe ui from that and depending on the event we can do something differently
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId :Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let{noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCase.getNote(noteId)?.also{note ->
                        currentNoteId = note.id
                        _notetitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }


    fun onEvent(event : AddEditNoteEvent){
       when(event){
           is AddEditNoteEvent.EnteredTitle ->{
               _notetitle.value = _notetitle.value.copy(
                   text = event.value
               )
           }
           is AddEditNoteEvent.ChangeTitleFocus -> {
               _notetitle.value = noteTitle.value.copy(
                   isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
               )
           }
           is AddEditNoteEvent.EnteredContent ->{
               _noteContent.value = noteContent.value.copy(
                   text = event.value
               )
           }
           is AddEditNoteEvent.ChangeContentFocus -> {
               _noteContent.value = noteContent.value.copy(
                   isHintVisible = !event.focusState.isFocused && _noteContent.value.text.isBlank()
               )
           }
           is AddEditNoteEvent.ChangeColor ->{
               _noteColor.value = event.color
           }
           is AddEditNoteEvent.SaveNote -> {
               viewModelScope.launch {
                   try {
                       noteUseCase.addNote(
                           Note(
                               title = noteTitle.value.text,
                               content = noteContent.value.text,
                               timeStamp = System.currentTimeMillis(),
                               color = noteColor.value,
                               id = currentNoteId
                           )
                       )
                       _eventFlow.emit(UiEvent.SaveNote)//we can react to that in the screen and simply navigate back
                   }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save Note"
                            )
                        )
                   }
               }
           }
       }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote :UiEvent()
    }

}

//when we click on existing note that we need the idea of that note in the screen
//and in this view model. How do we do that ? --> we just use a navigation argument.

