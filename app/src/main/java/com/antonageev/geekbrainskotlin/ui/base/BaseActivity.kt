package com.antonageev.geekbrainskotlin.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.consumeAsFlow
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KProperty

abstract class BaseActivity<S> : AppCompatActivity(), CoroutineScope {

    companion object{
        const val RC_SIGNIN = 199
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    private lateinit var dataJob : Job
    private lateinit var errorJob : Job


    abstract val viewModel: BaseViewModel<S>
    abstract val layoutRes : Int?

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            viewModel.getViewState().consumeEach {
                renderData(it)
            }
        }
        errorJob = launch {
            viewModel.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
    }

    protected fun renderError(t: Throwable?) {
        when (t) {
            is NoAuthException -> startLogin()
            else -> t?.message?.let {
                showError(it)
            }
        }
    }

    fun startLogin() {

        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder() //.setLogo()
                        .setTheme(R.style.LoginStyle)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGNIN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGNIN && resultCode != Activity.RESULT_OK) {
            finish()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    abstract fun renderData(data: S)

}