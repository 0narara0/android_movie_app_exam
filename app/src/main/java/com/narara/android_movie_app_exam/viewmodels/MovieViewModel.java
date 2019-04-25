package com.narara.android_movie_app_exam.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.narara.android_movie_app_exam.TMDB_Service;
import com.narara.android_movie_app_exam.models.Movie;
import com.narara.android_movie_app_exam.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieViewModel extends ViewModel {
    public MutableLiveData<List<Result>> results = new MutableLiveData<>();
    //public MutableLiveData<List<Result>> filteredResults = new MutableLiveData<>();

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
}
