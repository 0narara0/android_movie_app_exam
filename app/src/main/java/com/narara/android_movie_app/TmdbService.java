package com.narara.android_movie_app;

import com.narara.android_movie_app.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbService {
    @GET("3/search/movie?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getMovies(@Query("query") String query);

    @GET("3/search/movie?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getMovies(@Query("query") String query, @Query("page") int page);

    @GET("3/movie/popular?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getPopularMovies(@Query("page") int page);

    @GET("3/movie/now_playing?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getNowMovies(@Query("page") int page);

    @GET("3/movie/now_playing?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getTopMovies(@Query("page") int page);

    @GET("3/movie/now_playing?api_key=3283241144963ba613a482242cf1c715&language=ko-KR")
    Call<Movie> getUpcomingMovies(@Query("page") int page);

}
