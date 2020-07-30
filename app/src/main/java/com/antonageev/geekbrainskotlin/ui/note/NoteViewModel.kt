package com.antonageev.geekbrainskotlin.ui.note

import android.util.Log
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel
import java.lang.Error
import java.util.*

class NoteViewModel(val repository: NoteRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote : Note? = null
//TODO 56:44

//    fun save(note: Note) {
//        pendingNote = note
//    }

    fun save(newTitle: String, newBody: String, color : Note.Color) {
        pendingNote = pendingNote?.let {
            it.copy(
                    title = newTitle,
                    text = newBody,
                    lastState = Date(),
                    color = color
            )
        } ?: Note(UUID.randomUUID().toString(), newTitle, newBody, lastState = Date()) // color by default
    }
    //TODO observeOnce как в 57:03
    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever {result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = result.data as? Note))
                    pendingNote = viewStateLiveData.value?.data?.note
                }
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }

        }
    }

    fun deleteNote() {
        pendingNote?.let {
            repository.deleteNote(it.id).observeForever { result ->
                result ?: return@observeForever
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> {
                        pendingNote = null // чтобы заметка не пересохранялась в FireStore из onPause()
                        NoteViewState(NoteViewState.Data(isDeleted = true))
                    }
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }

            }
        }
    }

    override fun onCleared() {
//        pendingNote?.let {
//            saveToRepository(it)
//        }
    }

    fun saveToRepository() = pendingNote?.let { repository.saveNote(it) }
}