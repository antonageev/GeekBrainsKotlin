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
import kotlin.reflect.KProperty

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object{
        const val RC_SIGNIN = 199
    }

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes : Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
        setSupportActionBar(toolbar)

        viewModel.getViewState().observe(this, Observer {state ->
            state?.error?.let {
                renderError(it)
            } ?: let {
                renderData(state.data)
            }
        })

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

    abstract fun renderData(data: T)

}