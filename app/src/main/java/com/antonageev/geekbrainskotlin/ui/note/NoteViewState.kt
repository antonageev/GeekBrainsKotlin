package com.antonageev.geekbrainskotlin.ui.note

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseViewState


class NoteViewState(data: Data = Data(), error: Throwable? = null ) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted : Boolean = false, val note : Note? = null)
}