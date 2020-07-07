package com.antonageev.geekbrainskotlin.data

import com.antonageev.geekbrainskotlin.data.entity.Note

object NoteRepository {
    private val notes: List<Note> = listOf(
            Note("One", "text number one", 0xfff06292.toInt()),
            Note("Two", "text number two", 0xfff06292.toInt()),
            Note("Three", "text number three", 0xfff06292.toInt()),
            Note("Four", "text number four", 0xfff06292.toInt())
    )

    fun getNotes(): List<Note> {
        return notes
    }
}