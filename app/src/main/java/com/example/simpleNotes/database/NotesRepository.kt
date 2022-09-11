package com.example.simpleNotes.database

import androidx.lifecycle.LiveData

class NotesRepository(private val noteDao: NotesDao) {
    val getAllNotes : LiveData<List<Note>> = noteDao.getAll()

    suspend fun addNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }
}