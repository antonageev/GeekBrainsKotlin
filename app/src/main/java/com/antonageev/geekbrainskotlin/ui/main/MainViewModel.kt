package com.antonageev.geekbrainskotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel

class MainViewModel(val repository: NoteRepository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer { result : NoteResult ->
        when (result) {
            is NoteResult.Success<*> -> viewStateLiveData.value =  MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}