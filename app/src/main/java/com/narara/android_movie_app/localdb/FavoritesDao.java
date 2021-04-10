package com.narara.android_movie_app.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
