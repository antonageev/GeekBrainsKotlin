package com.antonageev.geekbrainskotlin.data

import com.antonageev.geekbrainskotlin.data.entity.Note

object NoteRepository {
    val notes: List<Note> = listOf(
            Note("One", "text number one", 0xff126100.toInt()),
            Note("Two", "text number two", 0xfff03000.toInt()),
            Note("Three", "text number three", 0xff0b9000.toInt()),
            Note("Four", "text number four", 0xff671080.toInt())
    )

//    fun getNotes(): List<Note> {
//        return notes
//    }
}