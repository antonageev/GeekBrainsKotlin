package com.antonageev.geekbrainskotlin.data

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.provider.RemoteDataProvider

class NoteRepository(val provider : RemoteDataProvider) {

    suspend fun saveNote(note: Note) = provider.saveNote(note)
    suspend fun getNoteById(id: String) = provider.getNoteById(id)
    fun getNotes() = provider.subscribeToAllNotes()
    suspend fun getCurrentUser() = provider.getCurrentUser()
    suspend fun deleteNote(noteId : String) = provider.deleteNote(noteId)
}
