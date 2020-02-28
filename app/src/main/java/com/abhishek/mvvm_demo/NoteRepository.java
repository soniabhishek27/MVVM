package com.abhishek.mvvm_demo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application)
    //application is a sub class of context
    {
        NoteDatabsae databsae = NoteDatabsae.getInstance(application);
        noteDao = databsae.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
        //Room does not allow database operations on the main thread

        new InsertNoteAsynkTask(noteDao).execute(note);

    }

    public void update(Note note)
    {
        new UpdateNoteAsynkTask(noteDao).execute(note);

    }

    public void delete(Note note)
    {

        new DeleteNotesAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes()
    {
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes()
    {
      return allNotes;

    }




    //INSERT QUERY
    public static class InsertNoteAsynkTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao daoo;

        private InsertNoteAsynkTask(NoteDao daoo)
        {
            this.daoo = daoo;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            daoo.insert(notes[0]);
            return null;
        }
    }

    //UPDATE
    public static class UpdateNoteAsynkTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        //we need this to make database operations

        //this class is static so we cannot access notDao directly thats why we need constructor
        private UpdateNoteAsynkTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.update(notes[0]);
            return null;

        }
    }

    //DELETE

    private static class DeleteNotesAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao daoo;

        public DeleteNotesAsyncTask(NoteDao daoo)
        {
            this.daoo = daoo;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            daoo.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void, Void>
    {
        private NoteDao daoo;

        public DeleteAllNotesAsyncTask(NoteDao daoo)
        {
            this.daoo = daoo;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            daoo.deleteAllNotes();
            return null;
        }
    }


}
