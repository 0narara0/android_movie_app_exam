package com.narara.android_movie_app_exam.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.narara.android_movie_app_exam.models.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
}
