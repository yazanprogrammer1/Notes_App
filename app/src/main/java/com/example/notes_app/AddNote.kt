package com.example.notes_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_kotlin.Data_Base.Data_Base_Holder
import com.example.notes_app.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //... code Add Notes
        // .....
        binding.etTitle.hint = "Title"
        binding.etNote.hint = "Note ..."
        var title = intent.getStringExtra("title").toString()
        var note = intent.getStringExtra("note").toString()
        var isUpdated = intent.getBooleanExtra("isUpdated", false)
        binding.etTitle.setText(title)
        binding.etNote.setText(note)

        val data_base = Data_Base_Holder(this)
        binding.imgCheck.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty() && binding.etNote.text.toString()
                    .isNotEmpty() && !isUpdated
            ) {

                val formatter = SimpleDateFormat("EEE, d MMM yyy HH:mm a")

                val inserted = data_base.insert_Note(
                    binding.etTitle.text.toString(),
                    binding.etNote.text.toString(),
                    formatter.format(Date()).toString()
                )
                if (inserted) {
                    Toast.makeText(this, "Insert Done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Not Insert", Toast.LENGTH_SHORT).show()
                }
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else if (binding.etTitle.text.toString()
                    .isNotEmpty() && binding.etNote.text.toString()
                    .isNotEmpty() && isUpdated
            ) {
                var id = data_base.get_id(title)
                data_base.edit_Data(
                    id,
                    binding.etTitle.text.toString(),
                    binding.etNote.text.toString()
                )
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgBackArrow.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }


    }
}