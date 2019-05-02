package com.narara.android_movie_app_exam.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.narara.android_movie_app_exam.TMDB_Service;
import com.narara.android_movie_app_exam.localdb.AppDatabase;
import com.narara.android_movie_app_exam.models.Movie;
import com.narara.android_movie_app_exam.models.Result;

import java.util.ArrayList;
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
    //public int currentPage = 1;


    public AppDatabase mDb;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mDb = Room.databaseBuilder(application,
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();

    }

    public void completeChanged(Result favorite, boolean isChecked) {
        if (isChecked) {
            mDb.favoritesDao().insertAll(favorite);
        } else {
            mDb.favoritesDao().deleteFavorite(favorite);
        }
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

    private TMDB_Service service = retrofit.create(TMDB_Service.class);


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
    public void fetchPopular() {
        service.getPopularMovies().enqueue(new Callback<Movie>() {
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

    public void fetchNow() {
        service.getNowMovies().enqueue(new Callback<Movie>() {
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

    public void fetchTop() {
        service.getTopMovies().enqueue(new Callback<Movie>() {
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

    public void fetchUpcoming() {
        service.getUpcomingMovies().enqueue(new Callback<Movie>() {
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
    /*
    * public void search(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getTitle().toLowerCase().trim()
                    .contains(query.toLowerCase().trim())) {
                filteredList.add(product);
            }
        }

        filteredProducts.setValue(filteredList);
    }
    * */

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

}
