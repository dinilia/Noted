package com.example.noted.ui.screen.notes

import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.noted.R
import com.example.noted.data.model.NoteModel
import com.example.noted.navigation.Route
import com.example.noted.ui.screen.notes.components.drawer.NavDrawer
import com.example.noted.ui.screen.notes.components.item.NotesItem
import com.example.noted.ui.screen.notes.components.navbar.NotesAppBar
import com.example.noted.ui.screen.notes.viewmodel.NotesPageBaseVM
import com.example.noted.ui.screen.notes.viewmodel.NotesPageMockVM
import com.example.noted.ui.screen.notes.viewmodel.NotesPageVM
import com.example.noted.utils.components.LockScreenOrientation
import com.example.noted.utils.components.dialog.LoadingDialog
import com.example.noted.utils.components.dialog.TextDialog
import kotlinx.coroutines.launch

@Composable
fun NotesPage(
    navHostController: NavHostController,
    viewModel: NotesPageBaseVM = hiltViewModel<NotesPageVM>()
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val checkedGridState = remember { mutableStateOf(false) }

    val noteListState = viewModel.noteList.observeAsState()
    val loadingState = viewModel.loader.observeAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }
    val filteredNoteListState = remember { mutableStateOf<List<NoteModel>>(listOf()) }
    val loadingDialog = remember { mutableStateOf(false) }
    val deleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = noteListState.value) {
        noteListState.value?.onFailure {
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = it.message ?: "",
                    withDismissAction = true
                )
            }
        }
    }

    LaunchedEffect(noteListState.value, viewModel.searchedTitleText.value) {
        filteredNoteListState.value = noteListState.value?.getOrNull()?.filter { note ->
            note.title.contains(viewModel.searchedTitleText.value, true)
        } ?: listOf()
    }

    LaunchedEffect(key1 = loadingState.value) {
        loadingDialog.value = (loadingState.value == true)
    }

    val deletedMessage = stringResource(id = R.string.note_is_successfully_deleted)

    fun deleteNoteList() {
        scope.launch {
            viewModel.deleteNoteList()
                .onSuccess {
                    deleteDialog.value = false
                    viewModel.unMarkAllNote()

                    snackBarHostState.showSnackbar(
                        message = deletedMessage,
                        withDismissAction = true
                    )
                }
                .onFailure {
                    deleteDialog.value = false

                    snackBarHostState.showSnackbar(
                        message = it.message ?: "",
                        withDismissAction = true
                    )
                }
        }
    }

    NavDrawer(
        drawerState = drawerState,
        content = {
            Scaffold(
                topBar = {
                    NotesAppBar(
                        isMarking = viewModel.isMarking.value,
                        markedNoteListSize = viewModel.markedNoteList.size,
                        isSearching = viewModel.isSearching.value,
                        searchedTitle = viewModel.searchedTitleText.value,
                        toggleDrawerCallback = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        selectAllCallback = {
                            viewModel.noteList.value?.getOrNull().let {
                                if (it != null) viewModel.markAllNote(it)
                            }
                        },
                        unSelectAllCallback = {
                            viewModel.unMarkAllNote()
                        },
                        onSearchValueChange = {
                            viewModel.searchedTitleText.value = it
                        },
                        closeMarkingCallback = {
                            viewModel.closeMarkingEvent()
                        },
                        deleteCallback = {
                            deleteDialog.value = true
                        },
                        checkedGridState = checkedGridState
                    )
                },
                snackbarHost = { SnackbarHost(snackBarHostState) },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            viewModel.closeMarkingEvent()
                            viewModel.closeSearchEvent()

                            navHostController.navigate(Route.NoteCreationPage.routeName)
                        },
                        text = {
                            Text(
                                stringResource(R.string.add),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.fab)
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                },
                content = { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)) {
                        filteredNoteListState.value.let {
                            if (it.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(200.dp)
                                            .clickable { }
                                            .padding(12.dp),
                                        imageVector = Icons.Default.SentimentVeryDissatisfied,
                                        contentDescription = "Settings",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "There are no any notes now!",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            } else {
                                checkedGridState.value.let { state ->
                                    if (state) {
                                        LazyVerticalGrid(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                            contentPadding = PaddingValues(
                                                top = 24.dp,
                                                bottom = 96.dp
                                            ),
                                            columns = GridCells.Fixed(2)
                                        ) {
                                            items(items = filteredNoteListState.value) {
                                                Box(Modifier.padding(start = 16.dp, end = 16.dp)) {
                                                    NotesItem(
                                                        isMarking = viewModel.isMarking.value,
                                                        isMarked = it in viewModel.markedNoteList,
                                                        data = it,
                                                        onClick = {
                                                            if (viewModel.isMarking.value) {
                                                                viewModel.addToMarkedNoteList(it)
                                                            } else {
                                                                viewModel.closeMarkingEvent()
                                                                viewModel.closeSearchEvent()

                                                                navHostController.navigate("${Route.NoteDetailPage.routeName}/${it.id}")
                                                            }
                                                        },
                                                        onLongClick = {
                                                            if (!viewModel.isMarking.value) {
                                                                viewModel.isMarking.value = true
                                                            }
                                                            viewModel.addToMarkedNoteList(it)
                                                        },
                                                        onCheckClick = {
                                                            viewModel.addToMarkedNoteList(it)
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                            contentPadding = PaddingValues(
                                                top = 24.dp,
                                                bottom = 96.dp
                                            )
                                        ) {
                                            items(items = filteredNoteListState.value) {
                                                Box(Modifier.padding(start = 16.dp, end = 16.dp)) {
                                                    NotesItem(
                                                        isMarking = viewModel.isMarking.value,
                                                        isMarked = it in viewModel.markedNoteList,
                                                        data = it,
                                                        onClick = {
                                                            if (viewModel.isMarking.value) {
                                                                viewModel.addToMarkedNoteList(it)
                                                            } else {
                                                                viewModel.closeMarkingEvent()
                                                                viewModel.closeSearchEvent()

                                                                navHostController.navigate("${Route.NoteDetailPage.routeName}/${it.id}")
                                                            }
                                                        },
                                                        onLongClick = {
                                                            if (!viewModel.isMarking.value) {
                                                                viewModel.isMarking.value = true
                                                            }
                                                            viewModel.addToMarkedNoteList(it)
                                                        },
                                                        onCheckClick = {
                                                            viewModel.addToMarkedNoteList(it)
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
            )
        }
    )

    LoadingDialog(isOpened = loadingDialog.value,
        onDismissCallback = { loadingDialog.value = false })

    TextDialog(isOpened = deleteDialog.value,
        onDismissCallback = { deleteDialog.value = false },
        onConfirmCallback = { deleteNoteList() })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotesPagePreview() {
    NotesPage(
        navHostController = rememberNavController(),
        viewModel = NotesPageMockVM()
    )
}