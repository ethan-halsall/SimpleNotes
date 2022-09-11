package com.example.simpleNotes.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val readAllNotes: LiveData<List<Note>>
    private val repository: NotesRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NotesRepository(noteDao)
        readAllNotes = repository.getAllNotes
    }

    fun getNoteLiveData(): LiveData<List<Note>> {
        return readAllNotes
    }

    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}