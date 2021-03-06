package com.antonageev.geekbrainskotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.common.getColorInt
import com.antonageev.geekbrainskotlin.data.entity.Note
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVAdapter(val onItemClick : ((Note) -> Unit)? = null ): RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes : List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(note: Note) : Unit {
            tv_title.text = note.title
            tv_text.text = note.text

            itemView.setBackgroundColor(note.color.getColorInt(itemView.context))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}