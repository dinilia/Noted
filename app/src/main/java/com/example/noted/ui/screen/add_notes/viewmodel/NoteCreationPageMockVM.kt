package com.example.noted.ui.screen.add_notes.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noted.data.model.NoteModel

class NoteCreationPageMockVM : ViewModel(), NoteCreationPageBaseVM {
    override val loader: MutableLiveData<Boolean> = MutableLiveData(false)
    override val titleText: MutableState<String> = mutableStateOf("")
    override val descriptionText: MutableState<String> = mutableStateOf("")
    override val imageFile: MutableState<Uri?> = mutableStateOf(null)

    override suspend fun addNote(note: NoteModel): Result<Boolean> {
        TODO("Not yet implemented")
    }
}