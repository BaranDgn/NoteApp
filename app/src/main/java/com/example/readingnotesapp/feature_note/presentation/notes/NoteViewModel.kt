package com.example.readingnotesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.domain.use_case.NoteUseCase
import com.example.readingnotesapp.feature_note.domain.util.NoteOrder
import com.example.readingnotesapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


//the job of the viewmodel in clean architecture is not
//business logic as it was plain MVVM
//instead job now is to actually make use of the use cases
//use cases contain the business logic, and viewmodel
//need to take the result of these use cases
//put all of that in a state that will represent or that relevant for UI
//so UI can observe on that state. can easily display that in readable way.
//That's the job of viewmodel.

//NoteUseCase --> yaratılan use case lerin wrap landıgı bir data classtır.
//viewmodel in constructor da instance ı nı olustrurarak
//icerisindekiuse case leri kullanabiliriz.
//mesela bu use caseleri kullanmak yerine view modelda bussiness logic yazdık,
//use case ler sayesinde aynı business logic i bir sure viewmodel de yazmaktan kurtulduk.
//Ayrıca use case sayesinde, business logic te bir degisiklik oldugunda, tek bir yerden degistirebilme imkanımızda var.

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCase
) : ViewModel(){

    //this will be the state that contains the values
    //UI will observe.
    private val _state= mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    //to keep reference in the view model to the last deleted node
    private var recentlyDeletedNote: Note?= null

    private var getNotesJob : Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.Order ->{
                //checking if the order type is the same noteOrder we wanted to change it to
                //and ordertype is ascending  or descending is the same as
                //this is already in our state.
                if(state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)

            }
            is NoteEvent.DeleteNote ->{
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvent.RestoreNote ->{
                viewModelScope.launch {
                    //To avoid of adding the note over and over again. We assure that add only once.
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToogleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach {notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}
