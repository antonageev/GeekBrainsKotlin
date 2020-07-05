package com.antonageev.geekbrainskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<String>()
    private val model = Model();

    init {
        model.getStringLiveData().observeForever {str ->
            viewStateLiveData.value = str
        }
    }

    fun viewState() : LiveData<String> = viewStateLiveData

}