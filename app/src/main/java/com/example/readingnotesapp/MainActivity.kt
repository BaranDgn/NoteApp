package com.example.readingnotesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readingnotesapp.feature_note.presentation.AddEditNote.AddEditNoteScreen
import com.example.readingnotesapp.feature_note.presentation.AddEditNote.ShareNote
import com.example.readingnotesapp.feature_note.presentation.notes.components.NoteScreen
import com.example.readingnotesapp.feature_note.presentation.util.Screen
import com.example.readingnotesapp.ui.theme.ReadingNotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingNotesAppTheme {
               Surface(
                   color = MaterialTheme.colors.background)
               {
                   val navController = rememberNavController()
                   NavHost(
                       navController = navController,
                       startDestination = Screen.NoteScreen.route ){

                        composable(route = Screen.NoteScreen.route){
                            NoteScreen(navController = navController)
                        }

                   
                       composable(route = Screen.AddEditNoteScreen.route +
                               "?noteId={noteId}&noteColor={noteColor}",

                           arguments = listOf(
                               navArgument(
                                   name = "noteId"
                               ){
                                   type = NavType.IntType
                                   defaultValue = -1
                               },
                               navArgument(
                                   name = "noteColor"
                               ){
                                   type = NavType.IntType
                                   defaultValue = -1
                               },
                           )

                       ){
                           val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color,
                                onShareClick = { title, content ->
                                    val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, title)
                                    putExtra(Intent.EXTRA_TEXT, content)
                                    type = "text/plain"
                                }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    startActivity(shareIntent)
                                }
                            )
                       }
                   }
               }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReadingNotesAppTheme {

    }
}