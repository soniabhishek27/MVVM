package com.abhishek.mvvm_demo;


// DAO here we define all the database operations that we want to make on
// NOTE ENTITY

//GENERALLY one DAO is Created for 1 ENTITY

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    //observe the changes
    LiveData<List<Note>> getAllNotes();





}
