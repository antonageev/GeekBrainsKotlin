package com.antonageev.geekbrainskotlin.ui.note

import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel(val repository: NoteRepository) : BaseViewModel<NoteData>() {

    private var pendingNote : Note? = null

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

    fun loadNote(noteId: String) = launch{
        try {
            repository.getNoteById(noteId).let {
                pendingNote = it
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let {
                repository.deleteNote(it.id)
                setData(NoteData(isDeleted = true))
                pendingNote = null // чтобы заметка не пересохранялась в FireStore из onPause()
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun saveToRepository() = launch {
        pendingNote?.let { repository.saveNote(it) }
    }
}