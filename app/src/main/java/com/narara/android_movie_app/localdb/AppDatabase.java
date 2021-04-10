package com.narara.android_movie_app.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.narara.android_movie_app.models.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
}
