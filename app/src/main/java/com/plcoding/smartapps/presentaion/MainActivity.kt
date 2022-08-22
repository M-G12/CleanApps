package com.gharibe.smartapps.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gharibe.smartapps.feature_note.domain.model.Note
import com.gharibe.smartapps.presentaion.add_edit_notes.components.AddEditNoteScreen
import com.gharibe.smartapps.presentaion.notes.NoteViewModel
import com.gharibe.smartapps.presentaion.notes.componenets.NoteScreen
import com.gharibe.smartapps.presentaion.util.Screen

import com.gharibe.smartapps.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val naveController = rememberNavController()
                    NavHost(
                        navController = naveController,
                        startDestination = Screen.NoteScreen.route
                    ) {
                        composable(
                            route = Screen.NoteScreen.route
                        ) {
                            NoteScreen(navController = naveController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = naveController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
