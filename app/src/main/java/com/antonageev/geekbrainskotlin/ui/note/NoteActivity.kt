package com.antonageev.geekbrainskotlin.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.common.getColorInt
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*
import org.koin.android.viewmodel.ext.android.viewModel

class NoteActivity : BaseActivity<NoteData>() {

    override val layoutRes = R.layout.activity_note

    companion object {

        private const val EXTRA_NOTE = "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    private var color : Note.Color = Note.Color.WHITE
    override val viewModel: NoteViewModel by viewModel()

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note)
        }
    }

    override fun renderData(data: NoteData) {

        if (data.isDeleted) finish()

        val note = data.note;
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastState)
        } ?: getString(R.string.new_note)

        note?.let {// поскольку не держим Note в Activity, то поле с цветом нужно сразу вытаскивать из полученных данных.
            color =  it.color
        }

        initView(note)
    }

    private fun initView(note: Note?) {


        note?.let {

            et_title.removeTextChangedListener(textChangeListener)
            et_body.removeTextChangedListener(textChangeListener)

            if (et_title.text.toString() != it.title) et_title.setTextKeepState(it.title)
            if (et_body.text.toString() != it.text) et_body.setTextKeepState(it.text)
            toolbar.setBackgroundColor(it.color.getColorInt(this))

            et_title.addTextChangedListener(textChangeListener)
            et_body.addTextChangedListener(textChangeListener)
        }

        colorPicker.onColorClickListener = {
            color = it
            toolbar.setBackgroundColor(color.getColorInt(this))
            saveNote()
        }
    }

    fun saveNote() {
        Log.wtf("NoteActivity", "saveNote invoked!!!")
        if (et_title.text.isNullOrBlank()) return
//
//        note = note?.copy(
//                title = et_title.text.toString(),
//                text = et_body.text.toString(),
//                lastState = Date()
//        ) ?: Note (id = UUID.randomUUID().toString(),
//                title = et_title.text.toString(),
//                text = et_body.text.toString()
//        )
//
//        note?.let {
//            viewModel.save(it)
//        }
        val title = et_title.text.toString()
        val body = et_body.text.toString()
        viewModel.save(title, body, color)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePalette(). let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveToRepository()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.note, menu).let { true }

    fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    fun deleteNote() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_confirmation))
                .setPositiveButton("Yes") { _, _ -> viewModel.deleteNote() }
                .setNegativeButton("No") {dialog, which ->  dialog.dismiss()}
                .show()
    }

}