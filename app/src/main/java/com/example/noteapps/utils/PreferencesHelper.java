package com.example.noteapps.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private SharedPreferences sharedPreferences = null;

    public void init(Context context) { //для инициализации
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void onSaveOnBoardState() { //для сохранения состояния onBoard
        sharedPreferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown() { //возвращаем
        return sharedPreferences.getBoolean("isShown", false);

    }

    public void saveImage() {
        sharedPreferences.edit().putBoolean("key", true).apply();
    }

    public void onSaveDefaultImage() {
        sharedPreferences.edit().putBoolean("key", false).apply();
    }

    public boolean isShownImage() {
        return sharedPreferences.getBoolean("key", false);
    }

}
