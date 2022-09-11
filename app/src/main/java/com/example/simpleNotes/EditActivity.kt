package com.example.simpleNotes

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.simpleNotes.database.Note
import com.example.simpleNotes.database.NotesViewModel
import com.example.simpleNotes.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    companion object {
        var oldNote : NoteModel? = null
    }

    private lateinit var binding: ActivityEditBinding
    private var hasTyped : Boolean = false

    lateinit var viewModel :  NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (oldNote != null){
            binding.textView.setText(oldNote!!.text)
            val simpleDate = SimpleDateFormat("dd/MM/yy hh:mm:ss")
            val date = simpleDate.format(Date(oldNote!!.date!! * 1000))
            binding.dateTextView.text = "Edited $date"
        }

        binding.textView.requestFocus()

        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]


        binding.textView.setOnFocusChangeListener { view, b ->
            if (view.isFocused) {
                if (!hasTyped && oldNote == null){
                    hasTyped = true
                    binding.textView.setText("")
                }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val timeStamp: Long = System.currentTimeMillis() / 1000L
        val text = binding.textView.text.toString()
        if (text != "Note" && text != "" && oldNote?.text != text) {
            val note = Note(oldNote?.id ?: 0, text, timeStamp)

            if (oldNote != null) {
                viewModel.updateNote(note)
            } else {
                viewModel.addNote(note)
            }
        }

        // Clear static variable before activity is killed
        oldNote = null
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}