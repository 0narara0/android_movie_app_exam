package com.narara.android_movie_app.localdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.narara.android_movie_app.models.Result;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM result")
    LiveData<List<Result>> getFavorite();

    @Query("SELECT * FROM result ORDER BY `order`")
    LiveData<List<Result>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Result result);

    @Insert
    void insertFavorites(List<Result> results);

    @Delete
    void deleteFavorite(Result result);

    @Query("DELETE FROM result")
    void deleteAll();
}
