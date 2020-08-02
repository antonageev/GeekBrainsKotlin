package com.antonageev.geekbrainskotlin.ui.splash

import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class SplashViewModel(val repository: NoteRepository) : BaseViewModel<Boolean>() {

    fun requestUser() = launch {
        repository.getCurrentUser()?.let {
            setData(true)
        } ?: let {
            setError(NoAuthException())
        }
    }

}