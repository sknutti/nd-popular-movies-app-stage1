package com.sknutti.popularmovies;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sknutti on 8/3/15.
 */
public interface tmdbApi {

    // http://api.themoviedb.org/3/discover/movie
//    @GET("/discover/movie")
//    void getTopMovies(@Query("sort_by") String sort, @Query("api_key") String apiKey, Callback<MovieList> response);
    @GET("/discover/movie")
    void getTopMovies(@Query("api_key") String apiKey, Callback<MovieList> response);

    // http://api.themoviedb.org/3/movie/135397
    @GET("/movie/{id}")
    void getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String appendToResponse, Callback<Movie> response);
}
