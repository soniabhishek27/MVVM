package com.abhishek.mvvm_demo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//Room database
@Database(entities = Note.class, version = 1)
public abstract class NoteDatabsae extends RoomDatabase{


    //to turn this class into singelton we cannot create multiple instances
    //singleton means we cannot create multiple instances of this database we use same instance everywhere
    private static NoteDatabsae instance;

    public abstract NoteDao noteDao();

    //noteDao is not initialized because room will take care of this this fun do will
    // access the daoo

     //synchronized because only one thread can access this at a time
    public static synchronized NoteDatabsae getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabsae.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            //build creates the database and initializes it
        }
        // if not null return the already existing instance
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedDbAsyncTask(instance).execute();
        }

    };

    private static class PopulatedDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private NoteDao noteDao;

        public  PopulatedDbAsyncTask(NoteDatabsae db)
        {
            noteDao = db.noteDao();


        }



        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1", "Hello From Android",1));
            noteDao.insert(new Note("Title2","Hello 2",2));
            noteDao.insert(new Note("Title3","Hello 3",3));
            return null;
        }
    }



}
