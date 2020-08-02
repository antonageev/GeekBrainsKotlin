package com.antonageev.geekbrainskotlin.ui.note

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseViewState


class NoteData(val isDeleted : Boolean = false, val note : Note? = null)