package com.antonageev.geekbrainskotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonageev.geekbrainskotlin.data.NoteRepository

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(NoteRepository.notes)
    }

    fun viewState() : LiveData<MainViewState> = viewStateLiveData

}