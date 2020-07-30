package com.antonageev.geekbrainskotlin.ui.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NoteRepository>()
    private val noteLiveData = MutableLiveData<NoteResult>()

    private val testNote = Note("1", "2", "3", color = Note.Color.WHITE)
    private lateinit var viewModel : NoteViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { mockRepository.getNoteById(testNote.id) } returns noteLiveData
        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
        every { mockRepository.saveNote(any()) } returns mockk()
        viewModel = NoteViewModel(mockRepository)
    }


    @Test
    fun `loadNote should return NoteData`() {
        var result : NoteViewState.Data? = null
        val testData = NoteViewState.Data(false, testNote)
        viewModel.getViewState().observeForever {
            result = it?.data
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Success(testNote)
        assertEquals(result, testData)
    }

    @Test
    fun `should return error`() {
        var result : Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it?.error
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Error(error = testData)
        assertEquals(result, testData)
    }

    @Test
    fun `delete should return NoteData with isDeleted`() {
        var result : NoteViewState.Data? = null
        val testData = NoteViewState.Data(true, null)
        every { mockRepository.deleteNote(any()) } returns noteLiveData //переопределили, т.к. различаются инстансы Note

        viewModel.getViewState().observeForever {
            result = it?.data
        }
        viewModel.save(testNote.title, testNote.text, testNote.color)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Success(null)
        assertEquals(result, testData)
    }

    @Test
    fun `delete should return error`() {
        var result : Throwable? = null
        val testData = Throwable("error")
        every { mockRepository.deleteNote(any()) } returns noteLiveData //переопределили, т.к. различаются инстансы Note

        viewModel.getViewState().observeForever {
            result = it?.error
        }
        viewModel.save(testNote.title, testNote.text, testNote.color)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Error(testData)
        assertEquals(result, testData)
    }

    @Test
    fun `should save changes`() {
        viewModel.save(testNote.title, testNote.text, testNote.color)
        viewModel.saveToRepository()
        verify(exactly = 1) { mockRepository.saveNote(any())}
    }
}