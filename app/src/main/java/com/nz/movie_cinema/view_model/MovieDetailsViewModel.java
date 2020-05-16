package com.nz.movie_cinema.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nz.movie_cinema.model.MovieCastCrew;
import com.nz.movie_cinema.model.MovieDetails;
import com.nz.movie_cinema.model.MovieVideos;
import com.nz.movie_cinema.model.SimilarMovies;
import com.nz.movie_cinema.rest.RestApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nz.movie_cinema.BaseConstants.API_KEY;

public class MovieDetailsViewModel extends AndroidViewModel {
    private MutableLiveData<MovieDetails> mutableMovieDetailsLiveData;
    private MutableLiveData<MovieCastCrew> mutableMovieCastCrewLiveData;
    private MutableLiveData<SimilarMovies> mutableSimilarMoviesLiveData;
    private MutableLiveData<MovieVideos> mutableMovieVideosLiveData;

    public MovieDetailsViewModel(Application application) {
        super(application);
        this.mutableMovieDetailsLiveData = new MutableLiveData<>();
        this.mutableMovieCastCrewLiveData = new MutableLiveData<>();
        this.mutableSimilarMoviesLiveData = new MutableLiveData<>();
        this.mutableMovieVideosLiveData = new MutableLiveData<>();
    }

    public LiveData<MovieDetails> getMovieDetails(int videoId) {
        return getMutableLiveDataOfMovieDetail(videoId);
    }

    public MutableLiveData<MovieDetails> getMutableLiveDataOfMovieDetail(int id) {

        Call<MovieDetails> call = RestApiFactory.create().getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails movie = response.body();
                if (movie != null) {
                    mutableMovieDetailsLiveData.setValue(movie);
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) { }
        });
        return mutableMovieDetailsLiveData;
    }

    public LiveData<MovieCastCrew> getMovieCastCrew(int videoId) {
        return getMutableLiveDataOfMovieCastCrew(videoId);
    }

    public MutableLiveData<MovieCastCrew> getMutableLiveDataOfMovieCastCrew(int id) {

        Call<MovieCastCrew> call = RestApiFactory.create().getMovieCastCrew(id,API_KEY);
        call.enqueue(new Callback<MovieCastCrew>() {
            @Override
            public void onResponse(Call<MovieCastCrew> call, Response<MovieCastCrew> response) {
                MovieCastCrew movie = response.body();
                if (movie != null) {
                    mutableMovieCastCrewLiveData.setValue(movie);
                }
            }

            @Override
            public void onFailure(Call<MovieCastCrew> call, Throwable t) { }
        });
        return mutableMovieCastCrewLiveData;
    }

    public LiveData<SimilarMovies> getSimilarMovies(int videoId) {
        return getMutableLiveDataOfSimilarMovies(videoId);
    }

    public MutableLiveData<SimilarMovies> getMutableLiveDataOfSimilarMovies(int id) {

        Call<SimilarMovies> call = RestApiFactory.create().getSimilarMovies(id,API_KEY);
        call.enqueue(new Callback<SimilarMovies>() {
            @Override
            public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                SimilarMovies movies = response.body();
                if (movies != null) {
                    mutableSimilarMoviesLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<SimilarMovies> call, Throwable t) {
                Log.e(MovieDetailsViewModel.class.getCanonicalName(), "(onFailure) : "+t.getMessage());
            }
        });
        return mutableSimilarMoviesLiveData;
    }

    public LiveData<MovieVideos> getMovieVideos(int videoId) {
        return getMutableLiveDataOfMovieVideos(videoId);
    }

    public MutableLiveData<MovieVideos> getMutableLiveDataOfMovieVideos(int id) {

        Call<MovieVideos> call = RestApiFactory.create().getMovieVideos(id,API_KEY);
        call.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                MovieVideos movies = response.body();
                if (movies != null) {
                    mutableMovieVideosLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
            }
        });
        return mutableMovieVideosLiveData;
    }
}
