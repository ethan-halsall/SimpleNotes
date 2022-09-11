package com.example.simpleNotes

import com.example.simpleNotes.database.Note

data class NoteModel(val note: Note) {
    var id : Int = note.id
    var text : String? = note.text
    var date: Long? = note.date
    var isExpanded: Boolean = false
}