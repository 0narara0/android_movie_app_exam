package com.narara.android_movie_app_exam.localdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.narara.android_movie_app_exam.models.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<Favorites>> getCartItems();

    @Insert
    void insertAll(Favorites... favorites);

    @Delete
    void deleteItem(Favorites favorites);
}
