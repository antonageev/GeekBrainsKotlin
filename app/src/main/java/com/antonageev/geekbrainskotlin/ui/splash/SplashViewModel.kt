package com.antonageev.geekbrainskotlin.ui.splash

import androidx.lifecycle.Observer
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.User
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel


class SplashViewModel() : BaseViewModel<Boolean?, SplashViewState>() {

//    private val observer = Observer{ newUser : User? ->
//        viewStateLiveData.value = newUser?.let {
//            SplashViewState(authenticated = true)
//        } ?: let {
//            SplashViewState(error = NoAuthException())
//        }
//    }
//
//    private val liveDataUser = NoteRepository.getCurrentUser()
//
//    fun requestUser() = liveDataUser.observeForever(observer)
//
//    override fun onCleared() {
//        super.onCleared()
//        liveDataUser.removeObserver(observer)
//    }

    //TODO тема с отпиской в onCleared не работает - зависает на SplashActivity
    // проверить.

    fun requestUser() = NoteRepository.getCurrentUser().observeForever{
        viewStateLiveData.value = it?.let {
            SplashViewState(authenticated = true)
        } ?: let {
            SplashViewState(error = NoAuthException())
        }
    }

}