package com.antonageev.geekbrainskotlin.data.common

import android.view.View

fun View.dip(value : Int) = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()
fun View.dip(value : Float) = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()