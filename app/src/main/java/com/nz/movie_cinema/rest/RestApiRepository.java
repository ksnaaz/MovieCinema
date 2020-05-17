package com.nz.movie_cinema.rest;

import android.arch.lifecycle.MutableLiveData;

import com.nz.movie_cinema.model.MoviePageResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nz.movie_cinema.BaseConstants.API_KEY;

public class RestApiRepository {
    private MutableLiveData<MoviePageResult> mutableLatestMoviesLiveData;

    public RestApiRepository(){
        mutableLatestMoviesLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<MoviePageResult> loadMovies(int page) {
        Call<MoviePageResult> call = RestApiFactory.create().getLatestMovies(page, API_KEY);
        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                MoviePageResult movie = response.body();
                if (movie != null) {
                    mutableLatestMoviesLiveData.setValue(movie);
                }
            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
            }
        });
        return mutableLatestMoviesLiveData;
    }
}
