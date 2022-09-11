package com.example.simpleNotes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleNotes.database.Note
import com.example.simpleNotes.database.NotesViewModel
import com.example.simpleNotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : NotesAdapter
    private lateinit var recyclerView : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        adapter = NotesAdapter()
        adapter.setViewModel(viewModel)
        viewModel.getNoteLiveData()
            .observe(this
            ) { notes ->
                val newNotes : ArrayList<NoteModel> = ArrayList()
                for (note in notes){
                    newNotes.add(NoteModel(note))
                }
                adapter.setNotes(newNotes)
            }

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setRoot(recyclerView)

        binding.add.setOnClickListener {
            val i = Intent(this, EditActivity::class.java)
            startActivity(i)
        }


    }




}