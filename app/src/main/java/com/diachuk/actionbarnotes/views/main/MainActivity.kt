package com.diachuk.actionbarnotes.views.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.data.entities.dto.NoteDTO
import com.diachuk.actionbarnotes.helpers.Constants.EDIT_ID_EXTRA
import com.diachuk.actionbarnotes.services.NoteService
import com.diachuk.actionbarnotes.views.add.AddActivity
import com.diachuk.actionbarnotes.views.main.components.NotesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences = get()

    private val vm: MainViewModel by viewModel()
    private val notesAdapter by lazy { get<NotesAdapter>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes_view.adapter = notesAdapter
        notes_view.layoutManager = LinearLayoutManager(this)

        notesAdapter.onEditClick = ::onEditClick
        notesAdapter.onDeleteClick = ::onDeleteClick

        vm.notes.observeForever {
            updateNotes(it)
        }

        add_btn.setOnClickListener {
            startAddActivity()
        }

        startService(Intent(this, NoteService::class.java))

        vm.onCreate()
    }

    private fun startAddActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    private fun updateNotes(notes: List<NoteDTO>) {
        notesAdapter.notes = notes
        notesAdapter.notifyDataSetChanged()
    }

    private fun onEditClick(id: Int) {
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra(EDIT_ID_EXTRA, id)
        startActivity(intent)
    }

    private fun onDeleteClick(id: Int) {
        vm.deleteNote(id)
    }
}