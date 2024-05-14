package com.example.noted.ui.screen.note_detail.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import com.example.noted.data.model.NoteModel

interface NoteDetailPageBaseVM {

    val loader: MutableLiveData<Boolean>

    var noteDetail: MutableLiveData<Result<NoteModel>>

    var titleText: MutableState<String>
    var descriptionText: MutableState<String>
    var imageValue: MutableState<Uri?>

    var isEditing: MutableState<Boolean>

    suspend fun getNoteDetail(id: Int)

    suspend fun updateNote(note: NoteModel): Result<Boolean>

    suspend fun deleteNoteList(vararg notes: NoteModel): Result<Boolean>
}