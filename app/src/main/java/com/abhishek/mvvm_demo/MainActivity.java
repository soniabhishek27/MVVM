package com.abhishek.mvvm_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
 NoteViewModel noteViewModel;
 TextView textView;
 FloatingActionButton buttonAddNote;

 String Color = "#fff";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddNote = findViewById(R.id.button_add_note);


        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });


        System.out.println(noteViewModel);

       RecyclerView recyclerView = findViewById(R.id.recycler_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       final NoteAdapter noteAdapter = new NoteAdapter();
       recyclerView.setAdapter(noteAdapter);


     noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

     noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
         @Override
         public void onChanged(List<Note> notes)
         {
            noteAdapter.setNotes(notes);
         }
     });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
             ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
     {
         @Override
         // on move is for drag and drop functionality
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
         {

             noteViewModel.delete(noteAdapter.getNotePosition(viewHolder.getAdapterPosition()));
             Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
         }
     }).attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desctiption = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);


            Note note = new Note(title, desctiption, priority);
            noteViewModel.insert(note);


            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        }







        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu_deleteall,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();

                Toast.makeText(MainActivity.this,"All Notes Deleted",Toast.LENGTH_SHORT).show();
                return true;

            default:  return super.onOptionsItemSelected(item);
        }



    }
}


