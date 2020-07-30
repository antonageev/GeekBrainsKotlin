package com.antonageev.geekbrainskotlin.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.User
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel


class SplashViewModel(val repository: NoteRepository) : BaseViewModel<Boolean?, SplashViewState>() {


    private var liveDataUser: LiveData<User>? = null

    private val observer = object : Observer<User> {
        override fun onChanged(newUser: User?) {
            viewStateLiveData.value = newUser?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
            liveDataUser?.removeObserver(this)
        }
    }


    fun requestUser() {
        liveDataUser = repository.getCurrentUser()
        liveDataUser?.observeForever(observer)
    }



    //TODO тема с отпиской в onCleared не работает - зависает на SplashActivity
    // проверить.

//    fun requestUser() = NoteRepository.getCurrentUser().observeForever{
//        viewStateLiveData.value = it?.let {
//            SplashViewState(authenticated = true)
//        } ?: let {
//            SplashViewState(error = NoAuthException())
//        }
//    }

}