package com.abhishek.mvvm_demo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Room anotation at compile time it will create
// all the code to create a sql table for this object


//this is a Entity Class

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;


    public Note(String title, String description, int priority)
    {
        this.title = title;
        this.description = description;
        this.priority = priority;

    }

    public void setId(int id)
    {
        this.id = id;

    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public int getPriority()
    {
        return priority;
    }
}
