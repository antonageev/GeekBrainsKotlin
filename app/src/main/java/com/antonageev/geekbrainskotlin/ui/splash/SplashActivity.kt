package com.antonageev.geekbrainskotlin.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.antonageev.geekbrainskotlin.ui.base.BaseActivity
import com.antonageev.geekbrainskotlin.ui.base.BaseViewModel
import com.antonageev.geekbrainskotlin.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel : SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override val layoutRes = null

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        Log.wtf("SplashActivity", " startMainActivity() ")
        finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }
}