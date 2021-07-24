package com.example.noteapps.utils;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.noteapps.room.NoteDatabase;

public class MyApp extends Application {
    public static NoteDatabase instance = null;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initSP();
        getInstance();
    }

    private void initSP() {
        PreferencesHelper preferencesHelper = new PreferencesHelper();
        preferencesHelper.init(this);
    }

    public static NoteDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    NoteDatabase.class, "note.database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
