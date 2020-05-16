package com.nz.movie_cinema.rest;

import com.nz.movie_cinema.model.MovieCastCrew;
import com.nz.movie_cinema.model.MovieDetails;
import com.nz.movie_cinema.model.MoviePageResult;
import com.nz.movie_cinema.model.MovieVideos;
import com.nz.movie_cinema.model.SimilarMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET("movie/now_playing")
    Call<MoviePageResult> getLatestMovies(@Query("api_key") String userkey);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String userkey);

    @GET("search/movie")
    Call<MoviePageResult> getSearchMovies(@Query("page") int page, @Query("api_key") String userkey, @Query("query") String query);

    @GET("movie/{movie_id}/credits")
    Call<MovieCastCrew> getMovieCastCrew(@Path("movie_id") int id, @Query("api_key") String userkey);

    @GET("movie/{movie_id}/similar")
    Call<SimilarMovies> getSimilarMovies(@Path("movie_id") int id, @Query("api_key") String userkey);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideos> getMovieVideos(@Path("movie_id") int id, @Query("api_key") String userkey);

}
