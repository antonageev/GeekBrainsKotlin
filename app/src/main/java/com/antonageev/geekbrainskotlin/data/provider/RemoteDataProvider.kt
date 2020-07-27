package com.antonageev.geekbrainskotlin.data.provider

import androidx.lifecycle.LiveData
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.entity.User
import com.antonageev.geekbrainskotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id : String): LiveData<NoteResult>
    fun saveNote(note : Note): LiveData<NoteResult>
    fun getCurrentUser() : LiveData<User>
}