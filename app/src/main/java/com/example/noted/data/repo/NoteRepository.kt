package com.example.noted.data.repo

import com.example.noted.data.local.NoteDAO
import com.example.noted.data.model.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val dao: NoteDAO
) {
    suspend fun getNoteList(sortBy: String, order: String): Flow<Result<List<NoteModel>>> {
        return dao.getNotes(sortBy, order).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(RuntimeException("Failed to get list of notes")))
        }
    }

    suspend fun deleteNoteList(vararg notes: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = (dao.deleteNotes(*notes) == notes.size)
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Failed to delete list of notes")))
        }

    suspend fun getNoteDetail(id: Int): Flow<Result<NoteModel>> {
        return dao.getNoteDetail(id).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(RuntimeException("Failed to get note detail")))
        }
    }

    suspend fun updateNoteList(note: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = dao.updateWithTimestamp(note) >= 1
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Failed to update list of notes")))
        }

    suspend fun insertNote(note: NoteModel): Flow<Result<Boolean>> =
        flow {
            val result = dao.insertWithTimestamp(note) != -1L
            emit(Result.success(result))
        }.catch {
            emit(Result.failure(RuntimeException("Failed to insert list of notes")))
        }
}