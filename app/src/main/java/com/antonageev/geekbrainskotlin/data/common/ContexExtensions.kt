package com.antonageev.geekbrainskotlin.data.common

import android.content.Context

fun Context.dip(value : Int) = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()
fun Context.dip(value : Float) = (value * (resources?.displayMetrics?.density ?: 0f)).toInt()