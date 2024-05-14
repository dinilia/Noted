package com.example.noted.ui.screen.notes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noted.data.model.NoteModel

interface NotesPageBaseVM {

    val loader: MutableLiveData<Boolean>

    val sortAndOrderData: MutableLiveData<Pair<String, String>>

    fun sortAndOrder(sortBy: String, orderBy: String)

    val noteList: LiveData<Result<List<NoteModel>>>

    val isSearching: MutableState<Boolean>
    val searchedTitleText: MutableState<String>

    val isMarking: MutableState<Boolean>
    val markedNoteList: SnapshotStateList<NoteModel>

    fun markAllNote(notes: List<NoteModel>)

    fun unMarkAllNote()

    fun addToMarkedNoteList(note: NoteModel)

    suspend fun deleteNoteList(vararg notes: NoteModel): Result<Boolean>

    fun closeMarkingEvent()

    fun closeSearchEvent()
}