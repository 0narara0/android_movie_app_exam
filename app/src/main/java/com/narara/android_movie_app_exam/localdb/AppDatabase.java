package com.narara.android_movie_app_exam.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
}
