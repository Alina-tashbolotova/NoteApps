package com.example.noteapps.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.noteapps.model.TaskModel;

@Database(entities = TaskModel.class,version = 3,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
   public abstract NoteDao noteDao();
}
