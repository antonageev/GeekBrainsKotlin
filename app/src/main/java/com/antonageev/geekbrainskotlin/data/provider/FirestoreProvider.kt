package com.antonageev.geekbrainskotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.antonageev.geekbrainskotlin.data.entity.Note
import com.antonageev.geekbrainskotlin.data.entity.User
import com.antonageev.geekbrainskotlin.data.error.NoAuthException
import com.antonageev.geekbrainskotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider : RemoteDataProvider {

    companion object{
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val store = FirebaseFirestore.getInstance()


    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser
    //TODO 1:42:44 - запихнуть currentUser в Delegate

    override fun getCurrentUser(): LiveData<User> = MutableLiveData<User>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()


    override fun subscribeToAllNotes(): LiveData<NoteResult> =  MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                value = e?.let {
                    NoteResult.Error(e)
                } ?: snapshot?.let {
                    val notes = it.documents.map { doc -> doc.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }

    }

    override fun getNoteById(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get().addOnSuccessListener {
                value = NoteResult.Success(it.toObject(Note::class.java))
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id).set(note).addOnSuccessListener {
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }
}