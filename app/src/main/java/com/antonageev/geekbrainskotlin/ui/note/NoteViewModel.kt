package com.antonageev.geekbrainskotlin.ui.note

import android.util.Log
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote : Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        NoteRepository.getNoteById(noteId).observeForever {result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }

        }
    }

    override fun onCleared() {
        Log.wtf("NoteViewModel", "onCleared invoked!!!")
        pendingNote?.let {
            NoteRepository.saveNote(it)
        }
    }
}