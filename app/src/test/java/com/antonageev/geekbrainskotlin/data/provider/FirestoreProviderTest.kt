package com.antonageev.geekbrainskotlin.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.antonageev.geekbrainskotlin.dsl.noteModule
import com.antonageev.geekbrainskotlin.ui.note.NoteViewState
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FirestoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()

    private val mockUsersCollection = mockk<CollectionReference>()
    private val mockUserDocument = mockk<DocumentReference>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val document1 = mockk<DocumentSnapshot>()
    private val document2 = mockk<DocumentSnapshot>()
    private val document3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val provider = FirestoreProvider(mockDb, mockAuth)

    @Before
    fun setUp() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { document1.toObject(Note::class.java) } returns testNotes[0]
        every { document2.toObject(Note::class.java) } returns testNotes[1]
        every { document3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `should throw NoAuthException if no auth`() {
        var result : Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `subscribeToAllNotes returns notes`() {
        var result : List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(document1, document2, document3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(result, testNotes)
    }

    @Test
    fun `subscribeToAllNotes returns error`() {
        var result : Throwable? = null
        val mockError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        slot.captured.onEvent(null, mockError)
        assertEquals(result, mockError)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDoc = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDoc
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDoc.set(testNotes[0]) }
    }

    @Test
    fun `saveNote returns note`() {
        var result : Note? = null
        val mockDocReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()
        every {mockResultCollection.document(testNotes[0].id)} returns mockDocReference
        every { mockDocReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()
        provider.saveNote(testNotes[0]).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }
        slot.captured.onSuccess(null)
        assertEquals(testNotes[0], result)
    }

    @Test
    fun `deleteNote calls delete`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(any()) } returns mockDocumentReference
        provider.deleteNote(testNotes[0].id)
        verify(exactly = 1) { mockDocumentReference.delete() }
    }

    @Test
    fun `deleteNote should return isDeleted true`() {
        var result : Boolean = false
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.delete().addOnSuccessListener(capture(slot)) } returns mockk()
        provider.saveNote(testNotes[0])
        provider.deleteNote(testNotes[0].id).observeForever {
            result = (it as NoteResult.Success<NoteViewState.Data>).data.isDeleted // бросает ClassCastException из-за возврата NoteResult.Error вместо Success
        }
        slot.captured.onSuccess(null)
        assertTrue(result)

    }

}