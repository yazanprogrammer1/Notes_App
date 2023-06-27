package com.example.notes_app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject_kotlin.Data_Base.Data_Base_Holder
import com.example.notes_app.Adapter.Notes_Adapter
import com.example.notes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //...... code Notes
        val data_base = Data_Base_Holder(this)
        val array_notes = data_base.get_AllNotes()
        binding.notesList.setHasFixedSize(true)
        binding.notesList.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        val adapter = Notes_Adapter(this, array_notes)
        binding.notesList.adapter = adapter


        binding.fbAddNote.setOnClickListener {
            val i = Intent(this, AddNote::class.java)
            startActivity(i)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}