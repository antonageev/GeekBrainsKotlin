package com.antonageev.geekbrainskotlin.ui.note

import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.ui.base.BaseViewState


class NoteViewState(note: Note? = null, error: Throwable? = null ) : BaseViewState<Note?>(note, error) {
}