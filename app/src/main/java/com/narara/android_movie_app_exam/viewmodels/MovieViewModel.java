package com.narara.android_movie_app_exam.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.narara.android_movie_app_exam.TmdbService;
import com.narara.android_movie_app_exam.localdb.AppDatabase;
import com.narara.android_movie_app_exam.models.Movie;
import com.narara.android_movie_app_exam.models.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieViewModel extends AndroidViewModel {

    public MutableLiveData<List<Result>> results = new MutableLiveData<>();
    public MutableLiveData<List<Result>> filteredResults = new MutableLiveData<>();
    public List<Result> resultList = new ArrayList<>();
    public int currentPage = 1;


    public AppDatabase mDb;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mDb = Room.databaseBuilder(application,
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration() //db 스키마 변경되면 이전 것은 날리고 새 db로 교체
                .build();

    }

    public void completeChanged(Result favorite, boolean isChecked) {
        if (isChecked) {
            mDb.favoritesDao().insertAll(favorite);
        } else {
            mDb.favoritesDao().deleteFavorite(favorite);
        }
    }

    public LiveData<List<Result>> getFavorites() {
        return mDb.favoritesDao().getAll();
    }

    public void update(List<Result> favorites) {
        for (int i = 0; i < favorites.size(); i++) {
            favorites.get(i).setOrder(i);
        }
        mDb.favoritesDao().deleteAll();
        mDb.favoritesDao().insertFavorites(favorites);
    }

    public void deleteFavorite(Result favorite) {
        mDb.favoritesDao().deleteFavorite(favorite);
    }

    // 즐겨찾기
    public LiveData<List<Result>> favorites() {
        return mDb.favoritesDao().getFavorite();
    }

    // Retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private TmdbService service = retrofit.create(TmdbService.class);


    public void fetchSearch(String search) {
        service.getMovies(search).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    results.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void fetchSearch(String search, int page) {
        service.getMovies(search, page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    if (results.getValue() == null) {
                        results.setValue(response.body().getResults());
                    } else {
                        List<Result> pageList = new ArrayList<>();
                        pageList.addAll(results.getValue());
                        pageList.addAll(response.body().getResults());
                        results.setValue(pageList);
                    }
                    currentPage = page;
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    public void fetch(String id, int page) {
        Callback<Movie> callback = new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    if (currentPage == 1) {
                        results.setValue(response.body().getResults());
                    } else if (results.getValue() != null) {
                        List<Result> pageList = new ArrayList<>();
                        pageList.addAll(results.getValue());
                        pageList.addAll(response.body().getResults());
                        results.setValue(pageList);
                    }
                    currentPage = page;
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        };

        switch (id) {
            case "popular":
                service.getPopularMovies(page).enqueue(callback);
                break;
            case "now":
                service.getNowMovies(page).enqueue(callback);
                break;
            case "top":
                service.getTopMovies(page).enqueue(callback);
                break;
            case "upcoming":
                service.getUpcomingMovies(page).enqueue(callback);
                break;
        }

    }

    public void search(String query) {
        List<Result> filteredList = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            Result result = resultList.get(i);
            if (result.getTitle().toLowerCase().trim()
                    .contains(query.toLowerCase().trim())) {
                filteredList.add(result);
            }
        }
        filteredResults.setValue(filteredList);
    }

    public void sort() {
        List<Result> resultList = results.getValue();
        Collections.sort(resultList, (o1, o2) -> o1.getRelease_date().compareTo(o2.getRelease_date()));
        results.setValue(resultList);
    }

}
