package com.antonageev.geekbrainskotlin.ui.main

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseViewState

class MainViewState (val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)