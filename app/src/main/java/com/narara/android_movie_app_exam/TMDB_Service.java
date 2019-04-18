package com.narara.android_movie_app_exam;

import com.narara.android_movie_app_exam.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDB_Service {
    @GET("3/search/movie?api_key=3283241144963ba613a482242cf1c715&language=ko-KR&page=2")
    Call<Movie> getMovies(@Query("query") String query);
}
