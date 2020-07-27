package com.antonageev.geekbrainskotlin.data

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.provider.RemoteDataProvider

class NoteRepository(val provider : RemoteDataProvider) {

    fun saveNote(note: Note) = provider.saveNote(note)

    fun getNoteById(id: String) = provider.getNoteById(id)

    fun getNotes() = provider.subscribeToAllNotes()

    fun getCurrentUser() = provider.getCurrentUser()

    fun deleteNote(noteId : String) = provider.deleteNote(noteId)
}
