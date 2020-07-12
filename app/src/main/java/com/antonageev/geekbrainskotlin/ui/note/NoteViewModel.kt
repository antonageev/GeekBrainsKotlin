package com.antonageev.geekbrainskotlin.ui.note

import androidx.lifecycle.ViewModel
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note

class NoteViewModel : ViewModel() {
    private var pendingNote : Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NoteRepository.saveNote(it)
        }
    }

}