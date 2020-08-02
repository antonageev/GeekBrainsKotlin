package com.antonageev.geekbrainskotlin.data.provider

import androidx.lifecycle.LiveData
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.entity.User
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id : String): Note
    suspend fun saveNote(note : Note): Note
    suspend fun getCurrentUser() : User?
    suspend fun deleteNote(noteId : String)
}