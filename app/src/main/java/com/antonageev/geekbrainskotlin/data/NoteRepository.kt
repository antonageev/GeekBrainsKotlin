package com.antonageev.geekbrainskotlin.data

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.provider.FirestoreProvider
import com.antonageev.geekbrainskotlin.data.provider.RemoteDataProvider

object NoteRepository {

    private val remoteProvider : RemoteDataProvider = FirestoreProvider()

    fun saveNote(note: Note) = remoteProvider.saveNote(note)

    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

    fun getNotes() = remoteProvider.subscribeToAllNotes()

    fun getCurrentUser() = remoteProvider.getCurrentUser()
}
