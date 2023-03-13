package com.example.readingnotesapp.feature_note.presentation.notes.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readingnotesapp.feature_note.data.model.Note
import com.example.readingnotesapp.feature_note.presentation.notes.NoteEvent
import com.example.readingnotesapp.feature_note.presentation.notes.NoteViewModel
import com.example.readingnotesapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
){
    //a reference to state we can get from the viewmodel
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditNoteScreen.route)
            },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ){

            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your Notes",
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = {
                    viewModel.onEvent(NoteEvent.ToogleOrderSection)
                },) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription ="Sort" )
                }
            }
            //Animation
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    })
            }
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                LazyColumn(modifier = androidx.compose.ui.Modifier.fillMaxSize()){
                    items(state.notes){note ->
                        NoteItem(
                            note = note,
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .clickable {
                                    //Navigation
                                           navController.navigate(
                                               Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                           )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(NoteEvent.DeleteNote(note))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Note deleted",
                                        actionLabel = "Undo"
                                    )
                                    //we clicked on the snackbar
                                    if(result == SnackbarResult.ActionPerformed){
                                        viewModel.onEvent(NoteEvent.RestoreNote)

                                    }
                                }
                            }
                        )
                        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                    }
                }
        }
    }
}
