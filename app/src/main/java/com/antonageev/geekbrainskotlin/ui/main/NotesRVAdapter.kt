package com.antonageev.geekbrainskotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.entity.Note

class NotesRVAdapter: RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes : List<Note> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}