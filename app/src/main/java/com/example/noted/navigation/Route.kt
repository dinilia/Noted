package com.example.noted.navigation

sealed class Route(val routeName: String) {
    data object NotesPage: Route(routeName = "notes_page")
    data object NoteDetailPage: Route(routeName = "note_detail_page")
    data object NoteCreationPage: Route(routeName = "note_creation_page")
}