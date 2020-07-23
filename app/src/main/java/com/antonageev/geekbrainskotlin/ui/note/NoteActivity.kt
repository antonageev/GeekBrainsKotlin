package com.antonageev.geekbrainskotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.common.getColorInt
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    override val layoutRes = R.layout.activity_note

    companion object {

        private const val EXTRA_NOTE = "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

//    private var note : Note? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

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

    override fun renderData(data: Note?) {
//        this.note = data
        supportActionBar?.title = data?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastState)
        } ?: getString(R.string.new_note)

        initView(data)
    }

    private fun initView(note: Note?) {
//        et_title.removeTextChangedListener(textChangeListener)
//        et_body.removeTextChangedListener(textChangeListener)

        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)
            toolbar.setBackgroundColor(it.color.getColorInt(this))
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
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
        viewModel.save(title, body)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
//            saveNote()
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveToRepository()
    }

}