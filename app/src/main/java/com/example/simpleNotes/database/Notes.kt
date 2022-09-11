package com.example.simpleNotes.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note_data") val text: String?,
    @ColumnInfo(name = "date") val date: Long?,
)

@Dao
interface NotesDao {
    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAll(): LiveData<List<Note>>

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Query("SELECT EXISTS(SELECT * FROM note WHERE id = :id)")
    fun doesNoteExist(id : Int) : Boolean

}


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            )
                //.allowMainThreadQueries()
                .build()
        }
    }

}



