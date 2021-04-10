package com.narara.android_movie_app;

import com.narara.android_movie_app.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbService {


    @GET("3/search/movie")
    Call<Movie> getMovies(@Query("api_key") String key,
                          @Query("query") String query,
                          @Query("language") String language);

    @GET("3/search/movie")
    Call<Movie> getMovies(@Query("api_key") String key,
                          @Query("query") String query,
                          @Query("page") int page,
                          @Query("language") String language);

    @GET("3/movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String key,
                                 @Query("page") int page,
                                 @Query("language") String language);

    @GET("3/movie/now_playing")
    Call<Movie> getNowMovies(@Query("api_key") String key,
                             @Query("page") int page,
                             @Query("language") String language);

    @GET("3/movie/top_rated")
    Call<Movie> getTopMovies(@Query("api_key") String key,
                             @Query("page") int page,
                             @Query("language") String language);

    @GET("3/movie/upcoming")
    Call<Movie> getUpcomingMovies(@Query("api_key") String key,
                                  @Query("page") int page,
                                  @Query("language") String language);


}
