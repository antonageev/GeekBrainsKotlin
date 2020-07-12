package com.antonageev.geekbrainskotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.antonageev.geekbrainskotlin.data.entity.Note
import java.util.*

object NoteRepository {

    private var notesLiveData = MutableLiveData<List<Note>>()

    private val notes: MutableList<Note> = mutableListOf(
            Note(
                    UUID.randomUUID().toString(),
                    "One",
                    "text number one",
                    color = Note.Color.YELLOW),
            Note(
                    UUID.randomUUID().toString(),
                    "Two",
                    "text number two",
                    color = Note.Color.BLUE),
            Note(
                    UUID.randomUUID().toString(),
                    "Three",
                    "text number three",
                    color = Note.Color.GREEN),
            Note(
                    UUID.randomUUID().toString(),
                    "Four",
                    "text number four",
                    color = Note.Color.VIOLET),
            Note(
                    UUID.randomUUID().toString(),
                    "Five",
                    "text number four",
                    color = Note.Color.WHITE),
            Note(
                    UUID.randomUUID().toString(),
                    "Six",
                    "text number four",
                    color = Note.Color.RED)
    )

    init {
        notesLiveData.value = notes
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    fun addOrReplace(note: Note) {
        for (i in 0 until notes.size) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }
}