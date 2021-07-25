package com.example.noteapps.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteapps.model.TaskModel;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM taskmodel")
    LiveData<List<TaskModel>> getAll();

    @Insert
    void insertNote(TaskModel... taskModels);

    @Delete
    void delete(TaskModel taskModel);

    @Update
    void update(TaskModel taskModel);
}


