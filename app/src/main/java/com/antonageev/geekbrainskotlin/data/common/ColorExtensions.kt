package com.antonageev.geekbrainskotlin.data.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.antonageev.geekbrainskotlin.R
import com.antonageev.geekbrainskotlin.data.entity.Note

fun Note.Color.getColorInt(context: Context) = ContextCompat.getColor(context, when (this) {
    Note.Color.WHITE -> R.color.color_white
    Note.Color.VIOLET -> R.color.color_violet
    Note.Color.YELLOW -> R.color.color_yellow
    Note.Color.RED -> R.color.color_red
    Note.Color.GREEN -> R.color.color_green
    Note.Color.BLUE -> R.color.color_blue
})