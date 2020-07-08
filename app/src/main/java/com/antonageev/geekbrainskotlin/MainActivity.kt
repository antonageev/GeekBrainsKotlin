package com.antonageev.geekbrainskotlin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    var helloBtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.viewState().observe(this, Observer {
            str -> showToast(str)
        })

        initViews()

        initListeners()
    }

    private fun showToast(str: String?) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

    private fun initViews() {
        helloBtn = findViewById(R.id.helloBtn)
    }

    private fun initListeners() {
        helloBtn?.setOnClickListener { v -> showToast(viewModel.viewState().value) }
    }

}