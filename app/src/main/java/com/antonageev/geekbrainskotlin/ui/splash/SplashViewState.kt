package com.antonageev.geekbrainskotlin.ui.splash

import com.antonageev.geekbrainskotlin.ui.base.BaseViewState

class SplashViewState(authenticated : Boolean? = null, error : Throwable? = null) : BaseViewState<Boolean?>(authenticated, error) {
}