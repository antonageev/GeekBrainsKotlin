package com.antonageev.geekbrainskotlin.ui.main

import androidx.lifecycle.Observer
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer { result : NoteResult ->
        when (result) {
            is NoteResult.Success<*> -> viewStateLiveData.value =  MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repository = NoteRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repository.observeForever(notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repository.removeObserver(notesObserver)
    }
}