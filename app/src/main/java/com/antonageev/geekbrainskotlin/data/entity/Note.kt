package com.antonageev.geekbrainskotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note (
        val id: String = "",
        val title: String = "",
        val text: String = "",
        val color: Color = Color.WHITE,
        val lastState: Date = Date()) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (this.id == other.id) return true

        return false
    }

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET
    }

}