package com.antonageev.geekbrainskotlin.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogoutDialog : DialogFragment() {

    companion object{
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context)
            .setTitle("Logout")
            .setMessage("Are you sure")
            .setPositiveButton("OK"){ dialog, which ->
                (activity as LogoutListener).onLogOut()
            }.setNegativeButton("Cancel") {dialog, which ->
                dismiss()
            }
            .create()
    // TODO убрать эту ерунду в Activity
    interface LogoutListener {
        fun onLogOut()
    }
}