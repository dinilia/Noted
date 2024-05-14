package com.example.noted.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.noted.ui.screen.add_notes.AddNotePage
import com.example.noted.ui.screen.note_detail.NoteDetailPage
import com.example.noted.ui.screen.notes.NotesPage

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Route.NotesPage.routeName
    ) {
        composable(Route.NotesPage.routeName) {
            NotesPage(navHostController = navHostController)
        }
        composable(
            route = "${Route.NoteDetailPage.routeName}/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            NoteDetailPage(
                navHostController = navHostController,
                id = it.arguments?.getString("noteId") ?: "0"
            )
        }
        composable(Route.NoteCreationPage.routeName) {
            AddNotePage(navHostController = navHostController)
        }
    }
}