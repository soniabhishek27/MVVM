package com.abhishek.mvvm_demo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteViewModel extends AndroidViewModel
//Android View Model is a sub class of View Model
{
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;




    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();

        //our activity later has reference to view model not to repository
    }

    public void insert(Note note)
    {
        repository.insert(note);
    }

    public void update(Note note)
    {
        repository.update(note);

    }

    public void delete(Note note)
    {

        repository.delete(note);
    }

    public void deleteAllNotes()
    {
        repository.deleteAllNotes();
    }

 //a method that return live data of list of notes
    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;

    }


}