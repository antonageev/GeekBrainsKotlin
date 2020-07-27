package com.antonageev.geekbrainskotlin.data.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format : String) = SimpleDateFormat(format, Locale.getDefault()).format(this)