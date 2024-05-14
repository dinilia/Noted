package com.example.noted.ui.screen.add_notes.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import com.example.noted.data.model.NoteModel

interface NoteCreationPageBaseVM {

    val loader: MutableLiveData<Boolean>

    val titleText: MutableState<String>
    val descriptionText: MutableState<String>
    val imageFile: MutableState<Uri?>

    suspend fun addNote(note: NoteModel): Result<Boolean>
}