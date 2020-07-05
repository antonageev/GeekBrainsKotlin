package com.antonageev.geekbrainskotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Model {

    private val mutableStringData = MutableLiveData<String>();

    init {
        mutableStringData.value = "Hello !!!"
    }

    fun getStringLiveData(): LiveData<String> = mutableStringData
}