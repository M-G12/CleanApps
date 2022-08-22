package com.plcoding.smartapps.presentaion.notes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gharibe.smartapps.di.AppModule
import com.gharibe.smartapps.presentaion.MainActivity
import com.gharibe.smartapps.presentaion.add_edit_notes.components.AddEditNoteScreen
import com.gharibe.smartapps.presentaion.notes.componenets.NoteScreen
import com.gharibe.smartapps.presentaion.util.Screen
import com.gharibe.smartapps.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            CleanArchitectureNoteAppTheme() {

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

    @Test
    fun saveNewNote_editAfterwards(){
        composeRule.onNodeWithContentDescription("Add note").performClick()
    }
}